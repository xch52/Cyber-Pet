import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import { useEffect, useState } from 'react';
import { useWeb3 } from '../Web3Context';


function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        Your Website
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

// TODO remove, this demo shouldn't need to reset the theme.

const defaultTheme = createTheme();

export default function SellWindows({ petsId }) {

  const { petNFT, petAuction, petMarket, web3, account } = useWeb3()
  const [price, setPrice] = React.useState('');
  const [duration, setDuration] = React.useState('');
  const [description, setDescription] = React.useState('');
  //const [tokenId, setTokenId] = useState(id); // 假设 id 是传入的 NFT tokenId

  const [isCheckboxChecked, setIsCheckboxChecked] = React.useState(false);
  const [saleType, setSaleType] = React.useState('auction');

  const isFormFilled = price && duration && isCheckboxChecked;

  const petApprove = async () => {
    if (!petNFT || (!petAuction && !petMarket) || !account) {
      alert('Please connect your wallet and ensure the contracts are loaded.');
      return;
    }

    try {
      let contractAddress = saleType === 'auction' ? petAuction.options.address : petMarket.options.address; // 选择合适的合约地址
      const tx = await petNFT.methods.approve(contractAddress, petsId).send({ from: account });
      console.log(`NFT has been approved to ${saleType === 'auction' ? 'auction' : 'market'} contract`);
      return tx;
    } catch (error) {
      console.error('Error approving NFT:', error);
      throw error; // 抛出异常供上层处理
    }
  };


  const clickSubmit = async (event) => {
    event.preventDefault();
    if (!web3 || !account || !petMarket) {
      alert('Please connect your wallet first.');
      return;
    }

    try {
      await petApprove();  // Ensure NFT is approved before transaction
    } catch (error) {
      console.error('Error approving NFT:', error);
      alert('Failed to approve NFT for the transaction.');
      return;  // If approval fails, stop execution
    }

    const priceWei = web3.utils.toWei(price, 'ether');
    const durationSeconds = parseInt(duration); // Convert duration to seconds
    console.log("account: ", account);


    try {
      if (saleType === 'auction') {
        await petAuction.methods.createAuction(petsId, priceWei, durationSeconds).send({ from: account });
        alert('Auction transaction create successfully!');
      } else if (saleType === 'normal') {
        await petMarket.methods.listPetForSale(petsId, priceWei).send({ from: account });
        alert('Normal transaction create successfully!');
      }
    } catch (error) {
      console.error('Error creating transaction:', error);
      alert('Failed to create transaction.');
    }
  };

  const saleTypeChange = (event, newSaleType) => {
    if (newSaleType !== null) {
      setSaleType(newSaleType);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <AddShoppingCartIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Transaction
          </Typography>
          <Box component="form" onSubmit={clickSubmit} noValidate sx={{ mt: 1 }}>

            {/* 交易类型选择 */}
            <Grid container alignItems="center" spacing={2}>
              <Grid item>
                <Typography component="h6" variant="subtitle1">
                  Sale Type :
                </Typography>
              </Grid>
              <Grid item>
                <ToggleButtonGroup
                  color="primary"
                  value={saleType}
                  exclusive
                  onChange={saleTypeChange}
                >
                  <ToggleButton value="auction" sx={{ width: 150 }}>
                    Auction
                  </ToggleButton>
                  <ToggleButton value="normal" sx={{ width: 150 }}>
                    Normal
                  </ToggleButton>
                </ToggleButtonGroup>
              </Grid>
            </Grid>

            {/* 起拍价与时间输入框 */}
            <Grid container spacing={2}>
              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="price"
                  label="Start Price (Ether)"
                  type="number"
                  name="price"
                  autoComplete="price"
                  autoFocus
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                />
              </Grid>
              <Grid item xs={6}>
                {saleType === 'auction' && (
                  <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="duration"
                    label="Duration (Seconds)"
                    type="number"
                    name="duration"
                    autoComplete="duration"
                    value={duration}
                    onChange={(e) => setDuration(e.target.value)}
                  />
                )}
              </Grid>
            </Grid>


            {/* 承诺勾选框 */}
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" checked={isCheckboxChecked}
                onChange={(e) => setIsCheckboxChecked(e.target.checked)} />}
              label="I've known the rules in the market."
            />

            {/* 创建交易按钮 */}
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={!price || !isCheckboxChecked || (saleType === 'auction' && !duration)} 
            >
              Create Transaction
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Helpful information
                </Link>
              </Grid>
              <Grid item>
                <Link href="#" variant="body2">
                  {"Find more"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>
  );
}