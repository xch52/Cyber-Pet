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
import HomeExample1 from '../assets/HomeExample1.jpg';
import HomeExample2 from '../assets/HomeExample2.jpg';
import Lottery from '../components/Lottery';
import { makeStyles } from '@mui/styles';
import { useEffect, useState } from 'react';
import { useWeb3 } from '../Web3Context'; 
import GaExample1 from '../assets/GaExample1.jpg';
// Image Source: https://lexica.art/prompt/b946a95c-e12e-4e8a-a92f-e3969d6e68fd
import GaExample2 from '../assets/GaExample2.jpg'
// Image Source: https://lexica.art/prompt/40f5c165-c1bf-4d46-947b-a0c575fad4a1




const sections = [
  { title: 'Home', url: '/' },
  { title: 'AuctionMarket', url: '/AuctionMarket' },
  { title: 'FreeMarket', url: '/FreeMarket' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'History', url: '/History' },
  { title: 'Portfolio', url: '/Portfolio' },
  //{ title: 'About us', url: '#' }
];

// const itemData = [
//   {
//     img: 'https://ipfs.io/ipfs/QmdXJpV7CQNSbm2zE4p4rWZgbDdumYfBfqdieG64FapVU3',
//     title: 'Cat1',
//   },
//   {
//     img: 'https://ipfs.io/ipfs/QmdXJpV7CQNSbm2zE4p4rWZgbDdumYfBfqdieG64FapVU3',
//     title: 'Dog1',
//   },
// ];

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

  const [products, setProducts] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch('http://44.202.121.86:9000/api/pets/1'); // 你的API端点
        const json = await response.json();
  
        if (json.code === 1 && json.msg === "success") {
          const productData = json.data;
          const newProduct = {
            id: productData.id,
            image: productData.imageUrl, // 从服务器获取图片URL
            title: productData.title,
            attributes: productData.attributes, // 直接使用从服务器获得的属性
            description: "This is a professional cat who loves adventure.", // 你可以根据需要从服务器获取或使用静态描述
            history: productData.history || [], // 使用从服务器获得的历史，或在无数据时使用空数组
            price: productData.price,
            states: productData.states,
            alt: "Product " + productData.id // 或任何其他适当的替代文本
          };
          setProducts([newProduct]); // 这将设置产品数组为包含单一产品的数组，或者你可以添加到现有数组中
          console.log(json.data);
        } else {
          console.error('Server response not successful:', json.msg);
        }
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    };

    fetchProducts();
  }, []);

  
  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />

        <Box sx={{ overflowX: 'auto' }}>
          <ImageList sx={{ flexWrap: 'nowrap', transform: 'translateZ(0)' }} cols={products.length} rowHeight={300}>
            {products.map((product) => (
              <ImageListItem key={product.id}>
                <img
                  src={product.image}
                  alt={product.alt || 'Product image'}
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

