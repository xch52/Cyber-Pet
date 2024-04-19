import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import GitHubIcon from '@mui/icons-material/GitHub';
import FacebookIcon from '@mui/icons-material/Facebook';
import XIcon from '@mui/icons-material/X';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from './Header';
import MainFeaturedPost from './MainFeaturedPost';
import FeaturedPost from './FeaturedPost';
import Main from './Main';
import Sidebar from './Sidebar';
import Footer from './Footer';
import post1 from './blog-post.1.md';
import post2 from './blog-post.2.md';
import post3 from './blog-post.3.md';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomeDesk from '../assets/HomeDesk.jpg';
// Image Source: https://lexica.art/prompt/aa863c1a-adc6-4ec3-a448-afba14d74fc3
import HomeExample1 from '../assets/HomeExample1.jpg';
// Image Source: https://lexica.art/prompt/aa863c1a-adc6-4ec3-a448-afba14d74fc3
import HomeExample2 from '../assets/HomeExample2.jpg';
// Image Source: https://lexica.art/prompt/aa863c1a-adc6-4ec3-a448-afba14d74fc3

const sections = [
  { title: 'Home', url: '/' },
  { title: 'Market', url: '/Market' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'Portfolio', url: '/Portfolio' },
  { title: 'About us', url: '#' }
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
    title: 'Free market',
    date: 'Nov 12',
    description:
      'In this market, you can trade your pets freely. When you find a pet you love, you can buy it without hesitation. You can also sell your pets.',
    image: HomeExample1,
    imageLabel: 'Image Text',
  },
  {
    title: 'Fascinating gashapon',
    date: 'Nov 11',
    description:
      'This is a wider card with supporting text below as a natural lead-in to additional content.',
    image: HomeExample2,
    imageLabel: 'Image Text',
  },
];

const posts = [post1, post2, post3];

const sidebar = {
  title: 'About',
  description:
    'Etiam porta sem malesuada magna mollis euismod. Cras mattis consectetur purus sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.',
  archives: [
    { title: 'March 2020', url: '#' },
    { title: 'February 2020', url: '#' },
    { title: 'January 2020', url: '#' },
    { title: 'November 1999', url: '#' },
    { title: 'October 1999', url: '#' },
    { title: 'September 1999', url: '#' },
    { title: 'August 1999', url: '#' },
    { title: 'July 1999', url: '#' },
    { title: 'June 1999', url: '#' },
    { title: 'May 1999', url: '#' },
    { title: 'April 1999', url: '#' },
  ],
  social: [
    { name: 'GitHub', icon: GitHubIcon },
    { name: 'X', icon: XIcon },
    { name: 'Facebook', icon: FacebookIcon },
  ],
};

// TODO remove, this demo shouldn't need to reset the theme.
const defaultTheme = createTheme();

export default function Blog() {
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
          <Grid container spacing={5} sx={{ mt: 3 }}>
            <Main title="From the firehose" posts={posts} />
            <Sidebar
              title={sidebar.title}
              description={sidebar.description}
              archives={sidebar.archives}
              social={sidebar.social}
            />
          </Grid>
        </main>
      </Container>
      <Footer
        title="Footer"
        description="Something here to give the footer a purpose!"
      />
    </ThemeProvider>
  );
}
