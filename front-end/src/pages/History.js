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
import SoldCard from '../components/SoldCard';
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

const sidebar = {
  title: 'About',
  description:
    "Here you can check the history of all transactions that have closed, whether they are auction transactions or ordinary transactions. I'm sure it will enhance your understanding of two markets as a whole.",
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

const products = [
  {
    // id: 1,
    // image: HomeExample1,
    // title: "Adventure Cat",
    // petclass: "1",
    // attribute: ["Cool", "Nice"],
    // description: "This is a professional cat who loves adventure.",
    // price: 3.5,
    // prebid: [0.1, 0.2, 0.3, 0.4, 0.5],
    // states: "1",
    // deadline: Math.floor(Date.now() / 1000) + 2000,
    // alt: "Product 1",
  },
  // 可以根据需要添加更多商品
];

const defaultTheme = createTheme();


export default function History() {

  const { petAuction, petNFT, web3 } = useWeb3();
  const [isLoading, setIsLoading] = useState(true);
  const [showClass1, setShowClass1] = useState(false);
  const [showClass2, setShowClass2] = useState(false);
  const [showClass3, setShowClass3] = useState(false);
  const [showOnSale, setShowOnSale] = useState(false);
  const [showSold, setShowSold] = useState(false);
  const [showExpired, setShowExpired] = useState(false);
  const [priceOrder, setPriceOrder] = useState('none');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [products, setProducts] = useState([]);
  const [soldProducts, setSoldProducts] = useState([]);
  const [viewState, setViewState] = useState('onSale');


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

  const [petNameFilter, setPetNameFilter] = useState('');

  const clearPetName = () => {
    setPetNameFilter(''); // 只清除宠物名称输入
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
    const nameCheck = product.title.toLowerCase().includes(petNameFilter.toLowerCase()); // 宠物名称过滤
    return classCheck && stateCheck && priceCheck && nameCheck;
  }).sort((a, b) => {
    if (priceOrder === 'highToLow') {    // 价格排序调整
      return b.price - a.price;
    } else if (priceOrder === 'lowToHigh') {
      return a.price - b.price;
    }
    return 0; // 选择 'none' 时保持默认顺序
  });


  // Auction地址： http://13.40.175.75:9000/api/pets/auction
  // Market地址： http://44.202.121.86:9000/api/pets/market

  // 获取正在拍卖的宠物信息
  useEffect(() => {
    async function fetchHistory() {
      try {
        // 获取Auction和Market的历史数据
        const auctionResponse = await fetch('http://13.40.175.75:9000/api/history/auction');
        const marketResponse = await fetch('http://13.40.175.75:9000/api/history/market');
        const auctionData = await auctionResponse.json();
        const marketData = await marketResponse.json();

        if (auctionData.code === 1 && marketData.code === 1) {
          const auctionProducts = auctionData.data.map(item => ({
            tokenId: item.tokenId,
            seller: item.seller,
            price: item.highestBid,
            startPrice: item.reservePrice,
            highestBid: item.highestBid,
            highestBidder: item.highestBidder,
            buyer: item.highestBidder,
            type: 'Auction',
            startTime: item.startTime,
            endTime: item.endTime,
          }));
          const marketProducts = marketData.data.map(item => ({
            tokenId: item.tokenId,
            seller: item.sellerId,
            buyer: item.buyerId,
            price: item.price,
            type: 'Market',
            endTime: item.dateTime
          }));

          // 合并两种产品列表
          const combinedProducts = [...auctionProducts, ...marketProducts];

          console.log("2 combined: ", combinedProducts);

          // 获取所有tokenId对应的宠物详情
          const petDetailsResponse = await fetch('http://13.40.175.75:9000/api/pets/all');
          const petDetailsData = await petDetailsResponse.json();

          if (petDetailsData.code === 1) {
            const petsDetailsMap = petDetailsData.data.reduce((acc, pet) => {
              acc[pet.id] = pet;
              return acc;
            }, {});

            // 合并宠物详细信息到历史记录
            const mergedProducts = combinedProducts.map(product => {
              const petDetails = petsDetailsMap[product.tokenId];
              if (!petDetails) {
                console.error('No details found for tokenId:', product.tokenId);
                return product; // 返回原始产品信息或可能定义的默认值
              }
              return {
                ...product,
                title: petDetails.title,
                imageUrl: petDetails.imageUrl,
                petclass: petDetails.petclass,
                attribute: petDetails.attributes.join(', '),
                description: petDetails.description
              };
            });

            setProducts(mergedProducts);
            console.log("3 combined: ", mergedProducts);
          }
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }

    fetchHistory();

    // const interval = setInterval(loadData, 30000);  // 每30秒刷新数据

    // return () => clearInterval(interval); 

  }, [petAuction, petNFT, web3]);







  // 有 Auctions 时

  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <Container maxWidth="lg">
        <Header title="CyberPet" sections={sections} />
        <main>

          <Grid container spacing={5} sx={{ mt: 3 }}>

            {/* 侧边栏显示卡 */}
            <Grid item xs={12} md={3}>
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
                  Pet Price
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
                      <DeleteIcon variant="contained" onClick={clearPrice} />
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

              {/* 名称搜索卡 */}
              <FormControl>
                <Typography variant="h5" gutterBottom sx={{ mt: 3 }} color="secondary">
                  Pet Name
                </Typography>

                <Box
                  component="form"
                  sx={{
                    display: 'flex',        // 将Box的显示设置为flex布局
                    justifyContent: 'space-between', // 使元素平均分布在Box中
                    '& > :not(style)': { m: 1, width: '20ch' }, // 调整TextField的宽度以适应Box
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <TextField
                    id="outlined-min"
                    label="Pet name"
                    variant="outlined"
                    value={petNameFilter}
                    onChange={(e) => setPetNameFilter(e.target.value)} />
                  <Stack direction="row" spacing={1}>
                    <IconButton >
                      <DeleteIcon variant="contained" onClick={clearPetName} />
                    </IconButton>
                  </Stack>
                </Box>
              </FormControl>
            </Grid>

            {/* 商品展示卡 */}
            <Grid item xs={12} md={9} container spacing={4} justifyContent="center">  {/* Adjust md value to change the width of the product grid */}
              {filteredProducts.map(product => (
                <Grid item xs={12} sm={6} md={4} key={product.id}>  {/* You can adjust the sizes here to fit more or fewer products per row */}
                  <SoldCard
                    image={product.image}
                    title={product.title}
                    petclass={product.petclass}
                    attribute={product.attribute}
                    description={product.description}

                    type={product.type}
                    seller={product.seller}
                    buyer={product.buyer}

                    highestBid={product.highestBid}
                    highestBidder={product.highestBidder}
                    startTime={product.startTime}
                    endTime={product.endTime}
                    //startPrice={startPrice}

                    price={`${product.price} ETH`}
                    prebid={product.prebid}
                    states={product.states}
                    deadline={product.deadline}

                    alt={product.alt}
                    tokenId={product.tokenId}
                    petAuction={petAuction}
                    web3={web3}
                  //account={account}
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

