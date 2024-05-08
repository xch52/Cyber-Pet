import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import GitHubIcon from '@mui/icons-material/GitHub';
import FacebookIcon from '@mui/icons-material/Facebook';
import XIcon from '@mui/icons-material/X';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeExample1 from '../assets/HomeExample1.jpg'
import HomeExample2 from '../assets/HomeExample2.jpg'
import SellCard from '../components/SellCard';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import Paper from '@mui/material/Paper';
import FormGroup from '@mui/material/FormGroup';
import Checkbox from '@mui/material/Checkbox';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import Stack from '@mui/material/Stack';
import DeleteIcon from '@mui/icons-material/Delete';
import { useWeb3 } from '../Web3Context';
import { useEffect, useState } from 'react';


const sections = [
  { title: 'Home', url: '/' },
  { title: 'AuctionMarket', url: '/AuctionMarket' },
  { title: 'FreeMarket', url: '/FreeMarket' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'History', url: '/History' },
  { title: 'Portfolio', url: '/Portfolio' },
  //{ title: 'About us', url: '#' }
];

const sidebar = {
  title: 'About',
  description:
    'In the market you can trade your pet according to your preference. In addition, you can use filters to make specific choices. You can also click each image to get more information',
  archives: [
    { title: 'Class 1 : Initial', url: '#' },
    { title: 'Class 2 : Mature', url: '#' },
    { title: 'Class 3 : Ultimate', url: '#' },

  ],
  social: [
    { name: 'GitHub', icon: GitHubIcon },
    { name: 'X', icon: XIcon },
    { name: 'Facebook', icon: FacebookIcon },
  ],
};

const products = [
  {
    // id: 1,
    // image: HomeExample1,
    // title: "Adventure Cat",
    // petclass: "1",
    // attribute: ["Cool", "Nice"],
    // description: "This is a professional cat who loves adventure.",
    // price: 3.5,
    // prebid: [0.1, 0.2, 0.3, 0.4, 0.5],
    // states: "1",
    // deadline: Math.floor(Date.now() / 1000) + 2000,
    // alt: "Product 1",
  },
  // 可以根据需要添加更多商品
];

const defaultTheme = createTheme();


export default function AuctionMarket() {

  const { petAuction, petNFT, web3 } = useWeb3();
  const [isLoading, setIsLoading] = useState(true);
  const [showClass1, setShowClass1] = useState(false);
  const [showClass2, setShowClass2] = useState(false);
  const [showClass3, setShowClass3] = useState(false);
  const [showOnSale, setShowOnSale] = useState(false);
  const [showSold, setShowSold] = useState(false);
  const [showExpired, setShowExpired] = useState(false);
  const [priceOrder, setPriceOrder] = useState('none');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [products, setProducts] = useState([]);
  const [viewState, setViewState] = useState('onSale');


  const classChange = (event, setter) => {
    setter(event.target.checked);
  };

  const priceOrderChange = (event) => {
    setPriceOrder(event.target.value);
  };

  const stateChange = (event, stateSetter) => {
    stateSetter(event.target.checked);
  };

  const clearPrice = () => {
    setMinPrice('');
    setMaxPrice('');
  };

  const filteredProducts = products.filter(product => {
    const classCheck = (showClass1 && product.petclass === "1") ||  // 宠物等级过滤器
      (showClass2 && product.petclass === "2") ||
      (showClass3 && product.petclass === "3") ||
      (!showClass1 && !showClass2 && !showClass3);
    const stateCheck = (showOnSale && product.states === "1") ||  // 出售状态过滤器
      (showSold && product.states === "0") ||
      (!showOnSale && !showSold);
    const priceCheck = (minPrice === '' || parseFloat(product.price) >= parseFloat(minPrice)) &&  // 价格过滤器
      (maxPrice === '' || parseFloat(product.price) <= parseFloat(maxPrice));
    return classCheck && stateCheck && priceCheck;
  }).sort((a, b) => {
    if (priceOrder === 'highToLow') {    // 价格排序调整
      return b.price - a.price;
    } else if (priceOrder === 'lowToHigh') {
      return a.price - b.price;
    }
    return 0; // 选择 'none' 时保持默认顺序
  });


  // 获取正在拍卖的宠物信息
  useEffect(() => {
    
    const fetchAuctions = async () => {
      if (!petAuction || !web3 || !petNFT) {
        console.log("Waiting for initialization...");
        return;
      }
        try {
          console.log('Contract address:', petAuction.options.address);
          console.log('Calling listActiveAuctionsInfo...');
          const data = await petAuction.methods.listActiveAuctionsInfo().call();
          console.log('Received raw data:', data);
    
          const tokenIds = data[0];
          const auctionsBrief = data[1];
          const petsBrief = data[2];
    
          console.log('Token IDs:', tokenIds);
          console.log('Auction Details:', auctionsBrief);
          console.log('Pet Details:', petsBrief);
          
          /* global BigInt */
          //const [tokenIds, auctionsBrief, petsBrief] = data;
          const newProducts = tokenIds.map((tokenId, index) => ({
            id: tokenId.toString(),
            image: `https://ipfs.io/ipfs/${petsBrief[index].uri}`,
            title: petsBrief[index].name,
            petclass: petsBrief[index].level.toString(),
            attribute: [petsBrief[index].appearance + ', ' + petsBrief[index].character] ,
            description: petsBrief[index].description,
            price: web3.utils.fromWei(auctionsBrief[index].highestBid.toString(), 'ether'),
            prebid: [],
            states: auctionsBrief[index].active ? "1" : "0",
            deadline: new Date(Number(auctionsBrief[index].endTime) * 1000).toLocaleString(),
            alt: "Product " + tokenId,

            seller:auctionsBrief[index].seller,
            startTime: new Date(Number(auctionsBrief[index].startTime) * 1000).toLocaleString(), // Auction start time in UNIX timestamp.
            endTime: new Date(Number(auctionsBrief[index].endTime) * 1000).toLocaleString(), // Auction end time in UNIX timestamp.
            highestBid: web3.utils.fromWei(auctionsBrief[index].highestBid.toString(), 'ether'), // Current highest bid amount.
            highestBidder: auctionsBrief[index].highestBidder, // Address of the current highest bidder.
            reservePrice: web3.utils.fromWei(auctionsBrief[index].reservePrice.toString(), 'ether'),
            active: auctionsBrief[index].active.toString(),
          }));
          setProducts(newProducts);
        } catch (error) {
          console.error('Error fetching auctions:', error.message, error.stack);
        }
      
    };
    console.log(petAuction)
    fetchAuctions();

  }, [petAuction, petNFT, web3]);


    return (
      <ThemeProvider theme={defaultTheme}>
        <CssBaseline />
        <Container maxWidth="lg">
          <Header title="CyberPet" sections={sections} />
          <main>

            <Grid container spacing={5} sx={{ mt: 3 }}>

              {/* 侧边栏显示卡 */}
              <Grid item xs={12} md={3}>
                <Paper elevation={0} sx={{ p: 2, bgcolor: 'grey.200' }}>
                  <Typography variant="h6" gutterBottom>
                    {sidebar.title}
                  </Typography>
                  <Typography>{sidebar.description}</Typography>
                </Paper>

                {/* 宠物等级选择卡 */}
                <Typography variant="h5" gutterBottom sx={{ mt: 3 }} color="secondary">
                  Pet Class
                </Typography>

                <FormGroup>
                  <FormControlLabel control={<Checkbox checked={showClass1} onChange={(e) => classChange(e, setShowClass1)} />} label="Class 1 : Initial" />
                  <FormControlLabel control={<Checkbox checked={showClass2} onChange={(e) => classChange(e, setShowClass2)} />} label="Class 2 : Mature" />
                  <FormControlLabel control={<Checkbox checked={showClass3} onChange={(e) => classChange(e, setShowClass3)} />} label="Class 3 : Ultimate" />
                </FormGroup>

                {/* 价格选项卡 */}
                <FormControl>
                  <Typography variant="h5" gutterBottom sx={{ mt: 3 }} color="secondary">
                    Price
                  </Typography>

                  <Box
                    component="form"
                    sx={{
                      display: 'flex',        // 将Box的显示设置为flex布局
                      justifyContent: 'space-between', // 使元素平均分布在Box中
                      '& > :not(style)': { m: 1, width: '10ch' }, // 调整TextField的宽度以适应Box
                    }}
                    noValidate
                    autoComplete="off"
                  >
                    <TextField
                      id="outlined-min"
                      label="Min"
                      variant="outlined"
                      value={minPrice}
                      onChange={(e) => setMinPrice(e.target.value)} />
                    <TextField
                      id="outlined-max"
                      label="Max"
                      variant="outlined"
                      value={maxPrice}
                      onChange={(e) => setMaxPrice(e.target.value)} />
                    <Stack direction="row" spacing={1}>
                      <IconButton >
                        <DeleteIcon variant="contained" onClick={clearPrice} />
                      </IconButton>
                    </Stack>
                  </Box>

                  <RadioGroup
                    name="price-order"
                    value={priceOrder}
                    onChange={priceOrderChange}
                  >
                    <FormControlLabel value="none" control={<Radio />} label="None" />
                    <FormControlLabel value="highToLow" control={<Radio />} label="From highest to lowest" />
                    <FormControlLabel value="lowToHigh" control={<Radio />} label="From lowest to highest" />
                  </RadioGroup>
                </FormControl>

                {/* 状态选项卡
                <Typography variant="h5" gutterBottom sx={{ mt: 3 }} color="secondary">
                  State
                </Typography>

                <FormGroup>
                  <FormControlLabel control={<Checkbox checked={showOnSale} onChange={(e) => stateChange(e, setShowOnSale)} />} label="On Sale" />
                  <FormControlLabel control={<Checkbox checked={showSold} onChange={(e) => stateChange(e, setShowSold)} />} label="Sold" />
                </FormGroup> */}

                {/* <RadioGroup
                  name="viewState"
                  value={viewState}
                  onChange={(e) => setViewState(e.target.value)}
                  >
                    <FormControlLabel value="onSale" control={<Radio />} label="On-Sale" />
                    <FormControlLabel value="sold" control={<Radio />} label="Sold" />
                </RadioGroup> */}
  

              </Grid>

              {/* 商品展示卡 */}
              <Grid item xs={12} md={9} container spacing={4} justifyContent="center">  {/* Adjust md value to change the width of the product grid */}
                {filteredProducts.map(product => (
                  <Grid item xs={12} sm={6} md={4} key={product.id}>  {/* You can adjust the sizes here to fit more or fewer products per row */}
                    <SellCard
                      image={product.image}
                      title={product.title}
                      petclass={product.petclass}
                      attribute={product.attribute}
                      description={product.description}
                      price={`${Math.max(product.highestBid, product.reservePrice)} ETH`}
                      prebid={product.prebid}
                      states={product.states}
                      deadline={product.deadline}
                      alt={product.alt}
                      tokenId={product.id}
                      petAuction={petAuction}
                      web3={web3}
                    //account={account}
                    />
                  </Grid>
                ))}
              </Grid>
            </Grid>
          </main>
        </Container>
        <Footer
          title="Footer"
          description="Hope you can enjony CyberPet!"
        />
      </ThemeProvider>
    );
}

