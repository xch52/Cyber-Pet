import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import Box from '@mui/material/Box';
import HomeExample1 from '../assets/HomeExample1.jpg'
import HomeExample2 from '../assets/HomeExample2.jpg'
import GaExample1 from '../assets/GaExample1.jpg'
// Image Source: https://lexica.art/prompt/b946a95c-e12e-4e8a-a92f-e3969d6e68fd
import GaExample2 from '../assets/GaExample2.jpg'
// Image Source: https://lexica.art/prompt/40f5c165-c1bf-4d46-947b-a0c575fad4a1

const sections = [
  { title: 'Home', url: '/' },
  { title: 'Market', url: '/Market' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'Portfolio', url: '/Portfolio' },
  { title: 'About us', url: '#' }
];

const itemData = [
  {
    img: HomeExample1,
    title: 'Cat1',
  },
  {
    img: HomeExample2,
    title: 'Dog1',
  },
  {
    img: GaExample1,
    title: 'Cat2',
  },
  {
    img: GaExample2,
    title: 'Dog2',
  },
  {
    img: HomeExample1,
    title: 'Camera',
 le: 'Mushrooms',
  },
];

const defaultTheme = createTheme();

export default function Blog() {
  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />

        <Box sx={{ overflowX: 'auto' }}>
          <ImageList sx={{ flexWrap: 'nowrap', transform: 'translateZ(0)' }} cols={itemData.length} rowHeight={250}>
            {itemData.map((item) => (
              <ImageListItem key={item.img}>
                <img
                  srcSet={`${item.img}?w=164&h=164&fit=crop&auto=format&dpr=2 2x`}
                  src={`${item.img}?w=164&h=164&fit=crop&auto=format`}
                  alt={item.title}
                  loading="lazy"
                />
              </ImageListItem>
            ))}
          </ImageList>
        </Box>
            
            
      </Container>
      <Footer
        title="Footer"
        description="Hope you can enjony CyberPet!"
      />
    </ThemeProvider>
  );
}

