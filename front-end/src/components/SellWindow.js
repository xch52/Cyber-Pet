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

export default function SellWindows() {

  const [price, setPrice] = React.useState('');
  const [duration, setDuration] = React.useState('');
  const [description, setDescription] = React.useState('');
  const [isCheckboxChecked, setIsCheckboxChecked] = React.useState(false);

  const isFormFilled = price && duration && description && isCheckboxChecked;

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      price: data.get('price'),
      description: data.get('description'),
    });
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
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <Grid container spacing={2}>
              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="price"
                  label="Start Price (Ether)"
                  name="price"
                  autoComplete="price"
                  autoFocus
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="duration"
                  label="Duration (S)"
                  name="duration"
                  autoComplete="duration"
                  value={duration}
                  onChange={(e) => setDuration(e.target.value)}
                />
              </Grid>
            </Grid>
            <TextField
              margin="normal"
              required
              fullWidth
              name="description"
              label="Description"
              type="description"
              id="description"
              autoComplete="current-description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" checked={isCheckboxChecked} onChange={(e) => setIsCheckboxChecked(e.target.checked)} />}
              label="I've known the rules in the market."
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={!isFormFilled}
            >
              Create Auction
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