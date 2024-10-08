import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from '../components/Header';
import MainFeaturedPost from '../components/MainFeaturedPost';
import FeaturedPost from '../components/FeaturedPost';
import Footer from '../components/Footer';
import HomeDesk from '../assets/HomeDesk.jpg';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// Image Source: https://lexica.art/prompt/aa863c1a-adc6-4ec3-a448-afba14d74fc3
import HomeExample1 from '../assets/HomeExample1.jpg';
// Image Source: https://lexica.art/prompt/aa863c1a-adc6-4ec3-a448-afba14d74fc3
import HomeExample2 from '../assets/HomeExample2.jpg';
// Image Source: https://lexica.art/prompt/aa863c1a-adc6-4ec3-a448-afba14d74fc3

const sections = [
  { title: 'Home', url: '/' },
  { title: 'AuctionMarket', url: '/AuctionMarket' },
  { title: 'FreeMarket', url: '/FreeMarket' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'History', url: '/History' },
  { title: 'Portfolio', url: '/Portfolio' },
  //{ title: 'About us', url: '#' }
];

const mainFeaturedPost = {
  title: 'CyberPet—An incredible tamagotchi game',
  description:
    "Here, you can cultivate your own unique Tamagotchi. As you invest more effort, the pet will also evolve.",
  image: HomeDesk,
  imageText: 'main image description',
  linkText: 'Find more…',
};

const featuredPosts = [
  {
    title: 'Amazing market',
    date: 'May 20',
    description:
      'In this market, you can trade your pets freely. When you find a pet you love, you can buy it without hesitation. You can also sell your pets according to your own preferences.',
    image: HomeExample1,
    imageLabel: 'Image Text',
  },
  {
    title: 'Fascinating gashapon',
    date: 'May 20',
    description:
      'In the gashapon, you can spend Ether for an opportunity to incubate your own new pet. Trust me, it is definitely a fun and surprising journey.',
    image: HomeExample2,
    imageLabel: 'Image Text',
  },
];


// TODO remove, this demo shouldn't need to reset the theme.
const defaultTheme = createTheme();

export default function HomePage() {
  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />
        <main>
          <MainFeaturedPost post={mainFeaturedPost} />
          <Grid container spacing={4}>
            {featuredPosts.map((post) => (
              <FeaturedPost key={post.title} post={post} />
            ))}
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
