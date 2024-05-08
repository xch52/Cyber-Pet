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
import SellFreeCard from '../components/SellFreeCard';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import Paper from '@mui/material/Paper';
import FormGroup from '@mui/material/FormGroup';
import Checkbox from '@mui/material/Checkbox';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import Stack from '@mui/material/Stack';
import DeleteIcon from '@mui/icons-material/Delete';
import { useWeb3 } from '../Web3Context';
import { useEffect, useState } from 'react';


const sections = [
  { title: 'Home', url: '/' },
  { title: 'AuctionMarket', url: '/AuctionMarket' },
  { title: 'FreeMarket', url: '/FreeMarket' },
  { title: 'Gashapon', url: '/Gashapon' },
  { title: 'History', url: '/History' },
  { title: 'Portfolio', url: '/Portfolio' },
  //{ title: 'About us', url: '#' }
];

const products = [
  {
    // id: 1,
    // image: HomeExample1,
    // title: "Adventure Cat",
    // petclass: "1",
    // attribute:["Cool","Nice"],
    // description: "This is a professional cat who loves adventure.",
    // price: 3.5,
    // prebid:[0.1,0.2,0.3,0.4,0.5],
    // states: "1",
    // deadline: Math.floor(Date.now() / 1000) + 2000,
    // alt: "Product 1",
  },
  // 可以根据需要添加更多商品
];

const defaultTheme = createTheme();

const sidebar = {
  title: 'About',
  description:
    'In the market you can trade your pet according to your preference. In addition, you can use filters to make specific choices. You can also click each image to get more information',
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


export default function FreeMarket() {

  const [showClass1, setShowClass1] = useState(false);
  const [showClass2, setShowClass2] = useState(false);
  const [showClass3, setShowClass3] = useState(false);
  const [showOnSale, setShowOnSale] = useState(false);
  const [showSold, setShowSold] = useState(false);
  const [showExpired, setShowExpired] = useState(false);
  const [priceOrder, setPriceOrder] = useState('none');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');

  const { petMarket, petNFT, web3 } = useWeb3();
  const [products, setProducts] = useState([]);

  const classChange = (event, setter) => {
    setter(event.target.checked);
  };

  const priceOrderChange = (event) => {
    setPriceOrder(event.target.value);
  };

  const stateChange = (event, stateSetter) => {
    stateSetter(event.target.checked);
  };

  const clearPrice = () => {
    setMinPrice('');
    setMaxPrice('');
  };


  const filteredProducts = products.filter(product => {
    const classCheck = (showClass1 && product.petclass === "1") ||  // 宠物等级过滤器
      (showClass2 && product.petclass === "2") ||
      (showClass3 && product.petclass === "3") ||
      (!showClass1 && !showClass2 && !showClass3);
    const stateCheck = (showOnSale && product.states === "1") ||  // 出售状态过滤器
      (showSold && product.states === "0") ||
      (!showOnSale && !showSold);
    const priceCheck = (minPrice === '' || parseFloat(product.price) >= parseFloat(minPrice)) &&  // 价格过滤器
      (maxPrice === '' || parseFloat(product.price) <= parseFloat(maxPrice));
    return classCheck && stateCheck && priceCheck;
  }).sort((a, b) => {
    if (priceOrder === 'highToLow') {    // 价格排序调整
      return b.price - a.price;
    } else if (priceOrder === 'lowToHigh') {
      return a.price - b.price;
    }
    return 0; // Return 0 to maintain original order when 'none'
  });



  useEffect(() => {
    const fetchActiveSales = async () => {
      if (petMarket && petNFT) {
        try {
          // 直接调用合约方法并等待结果
          const result = await petMarket.methods.listActiveSalesInfo().call();
          const tokenIds = result[0];
          const salesData = result[1];
    
          // 遍历 tokenIds，使用对应的销售数据
          const productsTemp = await Promise.all(tokenIds.map(async (tokenId, index) => {
            // 获取每个 tokenId 对应的宠物属性
            const attributes = await petNFT.methods.getPetAttributes(tokenId).call();
            return {
              id: tokenId,
              image: attributes.url,
              title: attributes.name,
              petclass: attributes.level.toString(),
              attribute: [attributes.appearance, attributes.character],
              description: attributes.description,
              price: web3.utils.fromWei(salesData[index].price.toString(), 'ether'),  // 确保转换为字符串，处理大数字
              prebid: [],
              states: salesData[index].isActive ? "1" : "0",  // 判断是否在售
              deadline: Math.floor(Date.now() / 1000) + 2000,  // 示例值，应从合约获取实际值
              alt: `Pet ${attributes.name}`,
            };
          }));
    
          setProducts(productsTemp);
        } catch (error) {
          console.error("Failed to fetch active sales:", error);
        }
      }
    };
    

    fetchActiveSales();
  }, [petMarket, petNFT]);

  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />
        <main>

          <Grid container spacing={5} sx={{ mt: 3 }}>
            {/* 侧边栏显示卡 */}
            <Grid item xs={12} md={3}>  {/* Adjust md value to change the width of the sidebar */}
              <Paper elevation={0} sx={{ p: 2, bgcolor: 'grey.200' }}>
                <Typography variant="h6" gutterBottom>
                  {sidebar.title}
                </Typography>
                <Typography>{sidebar.description}</Typography>
              </Paper>

              {/* 宠物等级选择卡 */}
              <Typography variant="h5" gutterBottom sx={{ mt: 3 }} color="secondary">
                Pet Class
              </Typography>

              <FormGroup>
                <FormControlLabel control={<Checkbox checked={showClass1} onChange={(e) => classChange(e, setShowClass1)} />} label="Class 1 : Initial" />
                <FormControlLabel control={<Checkbox checked={showClass2} onChange={(e) => classChange(e, setShowClass2)} />} label="Class 2 : Mature" />
                <FormControlLabel control={<Checkbox checked={showClass3} onChange={(e) => classChange(e, setShowClass3)} />} label="Class 3 : Ultimate" />
              </FormGroup>

              {/* 价格选项卡 */}
              <FormControl>
                <Typography variant="h5" gutterBottom sx={{ mt: 3 }} color="secondary">
                  Price
                </Typography>

                <Box
                  component="form"
                  sx={{
                    display: 'flex',        // 将Box的显示设置为flex布局
                    justifyContent: 'space-between', // 使元素平均分布在Box中
                    '& > :not(style)': { m: 1, width: '10ch' }, // 调整TextField的宽度以适应Box
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <TextField
                    id="outlined-min"
                    label="Min"
                    variant="outlined"
                    value={minPrice}
                    onChange={(e) => setMinPrice(e.target.value)} />
                  <TextField
                    id="outlined-max"
                    label="Max"
                    variant="outlined"
                    value={maxPrice}
                    onChange={(e) => setMaxPrice(e.target.value)} />
                  <Stack direction="row" spacing={1}>
                  <IconButton >
                       <DeleteIcon variant="contained" onClick={clearPrice}/>
                  </IconButton>
                  </Stack>
                </Box>

                <RadioGroup
                  name="price-order"
                  value={priceOrder}
                  onChange={priceOrderChange}
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
                  <SellFreeCard
                    tokenId={product.id}
                    image={product.image}
                    title={product.title}
                    petclass={product.petclass}
                    attribute={product.attribute}
                    description={product.description}
                    price={`${product.price} ETH`}
                    orignPrice={product.price}
                    states={product.states}
                    //deadline={product.deadline}
                    alt={product.alt}
                    web3={web3}
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

