import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import GitHubIcon from '@mui/icons-material/GitHub';
import FacebookIcon from '@mui/icons-material/Facebook';
import XIcon from '@mui/icons-material/X';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from '../components/Header';
import MainFeaturedPost from '../components/MainFeaturedPost';
import FeaturedPost from '../components/FeaturedPost';
import Main from '../components/Main';
import Sidebar from '../components/Sidebar';
import Footer from '../components/Footer';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeExample1 from '../assets/HomeExample1.jpg'
import HomeExample2 from '../assets/HomeExample2.jpg'
import ShowCard from '../components/ShowCard';


const sections = [
    { title: 'Home', url: '/' },
    { title: 'Market', url: '/Market' },
    { title: 'Gashapon', url: '/Gashapon' },
    { title: 'Portfolio', url: '/Portfolio' },
    { title: 'About us', url: '#' }
  ];

const defaultTheme = createTheme();

const products = [
  {
    id: 1,
    image: HomeExample1,
    title: "Adventure Cat",
    description: "This is a professional cat who loves adventure.",
    price: 3.5,
    alt: "Product 1",
  }
  // 可以根据需要添加更多商品
];

export default function Blog() {
    return (
      <ThemeProvider theme={defaultTheme}>
        <CssBaseline />
        <Container maxWidth="lg">
          <Header title="CyberPet" sections={sections} />

          <Grid container spacing={4} justifyContent="center">
          {products.map(product => (
            <Grid item xs={12} sm={6} md={3} key={product.id}>
              <ShowCard
                image={product.image}
                title={product.title}
                description={product.description}
                price={`${product.price} ETH`}
                alt={product.alt}
              />
            </Grid>
          ))}
        </Grid>


        </Container>
        <Footer
          title="Footer"
          description="Hope you can enjony CyberPet!"
        />
      </ThemeProvider>
    );
  }
  
