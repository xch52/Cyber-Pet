import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeExample1 from '../assets/HomeExample1.jpg'
import ShowCard from '../components/ShowCard';
import { useEffect, useState } from 'react';
import { useWeb3 } from '../Web3Context'; 

// 44.202.121.86
//

const sections = [
  { title: 'Home', url: '/' },
  { title: 'AuctionMarket', url: '/AuctionMarket' },
  { title: 'FreeMarket', url: '/FreeMarket' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'History', url: '/History' },
  { title: 'Portfolio', url: '/Portfolio' },
  //{ title: 'About us', url: '#' }
];
const defaultTheme = createTheme();

const products = [
  {
    id: 1,
    image: HomeExample1,
    title: "Adventure Cat",
    attributes: ['happy', 'sad'],
    description: "This is a professional cat who loves adventure.",
    history:[],
    price: 3.5,
    states: '1',
    alt: "Product 1",
  }
  // 可以根据需要添加更多商品
];

export default function PortPage() {
    return (
      <ThemeProvider theme={defaultTheme}>
        <CssBaseline />
        <Container maxWidth="lg">
          <Header title="CyberPet" sections={sections} />

          <Grid container spacing={4} justifyContent="center">
          {products.map(product => (
            <Grid item xs={12} sm={6} md={3} key={product.id}>
              <ShowCard
                id={product.id}
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
  
