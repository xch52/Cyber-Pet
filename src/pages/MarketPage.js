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
import { useState } from 'react';
import Paper from '@mui/material/Paper';
import FormGroup from '@mui/material/FormGroup';
import Checkbox from '@mui/material/Checkbox';


const sections = [
  { title: 'Home', url: '/' },
  { title: 'Market', url: '/Market' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'Portfolio', url: '/Portfolio' },
  { title: 'About us', url: '#' }
];

const products = [
  {
    id: 1,
    image: HomeExample1,
    title: "Adventure Cat",
    petclass: "1",
    description: "This is a professional cat who loves adventure.",
    price: 3.5,
    alt: "Product 1",
  },
  {
    id: 2,
    image: HomeExample2,
    title: "Fashionable Dog",
    petclass: "2",
    description: "This is a dog who loves fashion and music.",
    price: 2.1,
    alt: "Product 2",
  },
  {
    id: 3,
    image: HomeExample1,
    title: "Adventure Cat",
    petclass: "1",
    description: "This is a professional cat who loves adventure.",
    price: 1.8,
    alt: "Product 3",
  },
  {
    id: 4,
    image: HomeExample2,
    title: "Fashionable Dog",
    petclass: "2",
    description: "This is a dog who loves fashion and music.",
    price: 0.9,
    alt: "Product 4",
  },
  {
    id: 5,
    image: HomeExample2,
    title: "Fashionable Dog",
    petclass: "2",
    description: "This is a dog who loves fashion and music.",
    price: 1.2,
    alt: "Product 5",
  },
  {
    id: 6,
    image: HomeExample1,
    title: "Adventure Cat",
    petclass: "3",
    description: "This is a professional cat who loves adventure.",
    price: 3.1,
    alt: "Product 6",
  },
  // 可以根据需要添加更多商品
];

const defaultTheme = createTheme();

const sidebar = {
  title: 'About',
  description:
    'In the market you can trade your pet according to your preference. In addition, you can use filters to make specific choices.',
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


export default function MarketPage() {

  const [showClass1, setShowClass1] = useState(false);
  const [showClass2, setShowClass2] = useState(false);
  const [showClass3, setShowClass3] = useState(false);
  const [priceOrder, setPriceOrder] = useState('none');


  const handleClassChange = (event, setter) => {
    setter(event.target.checked);
  };

  const handlePriceOrderChange = (event) => {
    setPriceOrder(event.target.value);
  };

  const filteredProducts = products.filter(product => {
    return (showClass1 && product.petclass === "1") ||
      (showClass2 && product.petclass === "2") ||
      (showClass3 && product.petclass === "3") ||
      (!showClass1 && !showClass2 && !showClass3);
  }).sort((a, b) => {
    if (priceOrder === 'highToLow') {
      return b.price - a.price;
    } else if (priceOrder === 'lowToHigh') {
      return a.price - b.price;
    }
    return 0; // Return 0 to maintain original order when 'none'
  });

  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />
        <main>

          <Grid container spacing={5} sx={{ mt: 3 }}>
            {/* Sidebar Grid item */}
            <Grid item xs={12} md={3}>  {/* Adjust md value to change the width of the sidebar */}
              <Paper elevation={0} sx={{ p: 2, bgcolor: 'grey.200' }}>
                <Typography variant="h6" gutterBottom>
                  {sidebar.title}
                </Typography>
                <Typography>{sidebar.description}</Typography>
              </Paper>

              {/* 宠物等级选择卡 */}
              <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>
                Pet Class
              </Typography>

              <FormGroup>
                <FormControlLabel control={<Checkbox checked={showClass1} onChange={(e) => handleClassChange(e, setShowClass1)} />} label="Class 1 : Initial" />
                <FormControlLabel control={<Checkbox checked={showClass2} onChange={(e) => handleClassChange(e, setShowClass2)} />} label="Class 2 : Mature" />
                <FormControlLabel control={<Checkbox checked={showClass3} onChange={(e) => handleClassChange(e, setShowClass3)} />} label="Class 3 : Ultimate" />
              </FormGroup>

              {/* 价格选项卡 */}
              <FormControl>
                <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>
                  Price
                </Typography>
                <RadioGroup
                  name="price-order"
                  value={priceOrder}
                  onChange={handlePriceOrderChange}
                >
                  <FormControlLabel value="none" control={<Radio />} label="None" />
                  <FormControlLabel value="highToLow" control={<Radio />} label="From highest to lowest" />
                  <FormControlLabel value="lowToHigh" control={<Radio />} label="From lowest to highest" />
                </RadioGroup>
              </FormControl>

            </Grid>

            {/* 商品展示卡 */}
            <Grid item xs={12} md={9} container spacing={4} justifyContent="center">  {/* Adjust md value to change the width of the product grid */}
              {filteredProducts.map(product => (
                <Grid item xs={12} sm={6} md={4} key={product.id}>  {/* You can adjust the sizes here to fit more or fewer products per row */}
                  <SellCard
                    image={product.image}
                    title={product.title}
                    petclass={product.petclass}
                    description={product.description}
                    price={`${product.price} ETH`}
                    alt={product.alt}
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

