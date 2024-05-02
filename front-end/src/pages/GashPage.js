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
import Lottery from '../components/Lottery'
// Image Source: https://lexica.art/prompt/b946a95c-e12e-4e8a-a92f-e3969d6e68fd
import GaExample2 from '../assets/GaExample2.jpg'
// Image Source: https://lexica.art/prompt/40f5c165-c1bf-4d46-947b-a0c575fad4a1



import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import StarIcon from '@mui/icons-material/StarBorder';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import { makeStyles } from '@mui/styles';


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

const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none',
    },
    '0%': {
      transform: 'translateX(0)',
    },
    '100%': {
      transform: 'translateX(-100%)', // 根据实际内容长度调整
    },
    '@keyframes scrollRight': {
      '0%': {
        transform: 'translateX(-100%)',
      },
      '100%': {
        transform: 'translateX(0)',
      },
    },
  },
  scrollContainer: {
    display: 'flex',
    overflowX: 'auto',
    animation: 'scrollRight 40s linear infinite', // 修改为从左向右的动画
    '&::-webkit-scrollbar': {
      display: 'none', // 隐藏滚动条
    }
  },
  appBar: {
    borderBottom: `1px solid ${theme.palette.divider}`,
  },
  toolbar: {
    flexWrap: 'wrap',
  },
  toolbarTitle: {
    flexGrow: 1,
  },
  link: {
    margin: theme.spacing(1, 1.5),
  },
  heroContent: {
    padding: theme.spacing(8, 0, 6),
  },
  cardHeader: {
    backgroundColor:
      theme.palette.type === 'light' ? theme.palette.grey[200] : theme.palette.grey[700],
  },
  cardPricing: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'baseline',
    marginBottom: theme.spacing(2),
  },
  footer: {
    borderTop: `1px solid ${theme.palette.divider}`,
    marginTop: theme.spacing(8),
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
    [theme.breakpoints.up('sm')]: {
      paddingTop: theme.spacing(6),
      paddingBottom: theme.spacing(6),
    },
  },
}));

const footers = [
  {
    title: 'Company',
    description: ['Team', 'History', 'Contact us', 'Locations'],
  },
  {
    title: 'Features',
    description: ['Cool stuff', 'Random feature', 'Team feature', 'Developer stuff', 'Another one'],
  },
  {
    title: 'Resources',
    description: ['Resource', 'Resource name', 'Another resource', 'Final resource'],
  },
  {
    title: 'Legal',
    description: ['Privacy policy', 'Terms of use'],
  },
];



export default function GashPage() {
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

        <Lottery />
        
      </Container>
      <Footer
        title="Footer"
        description="Hope you can enjony CyberPet!"
      />
    </ThemeProvider>
  );
}

