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
    // id: 1,
    // image: HomeExample1,
    // title: "Adventure Cat",
    // petclass: "1",
    // attributes: ['happy', 'sad'],
    // description: "This is a professional cat who loves adventure.",
    // history:[],
    // price: 3.5,
    // states: '1',
    // alt: "Product 1",
  }
  // 可以根据需要添加更多商品
];


export default function PortPage() {

  const { petNFT, account } = useWeb3(); // 获取PetNFT合约实例
  const [products, setProducts] = useState([]); // 用于存储获取的宠物数据

  useEffect(() => {
    const fetchPets = async () => {
      if (petNFT && account) {
        try {
          const result = await petNFT.methods.getMyPets().call({ from: account });
          const tokenIds = result[0];
          const petsAttributes = result[1];
  
          // Check if the received data is structured as expected
          if (!Array.isArray(tokenIds) || !Array.isArray(petsAttributes)) {
            console.error("Received data is not in expected array format:", result);
            return;
          }
  
          // Map over the tokenIds and use the index to access attributes in petsAttributes
          const formattedPets = tokenIds.map((tokenId, index) => {
            const pet = petsAttributes[index];
            return {
              id: tokenId.toString(),
              image: pet.url,
              title: pet.name,
              petclass: pet.level.toString(),
              attribute: [pet.appearance, pet.character],
              description: pet.description,
              price: `Level ${pet.level}`,
              alt: `Pet ${pet.name}`,
            };
          });
  
          setProducts(formattedPets);
        } catch (error) {
          console.error("Failed to fetch pets:", error);
        }
      }
    };
  
    fetchPets();

    const interval = setInterval(fetchPets, 5000);  // 每5秒刷新数据

    return () => clearInterval(interval); 
    
  }, [petNFT, account]); 


  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />

        <Grid container spacing={4} justifyContent="center">
          {products.map(product => (
            <Grid item xs={12} sm={6} md={3} key={product.id}>
              <ShowCard
                tokenId={product.id}
                image={product.image}
                title={product.title}
                petclass={product.petclass}
                attribute={product.attribute}
                description={product.description}
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

