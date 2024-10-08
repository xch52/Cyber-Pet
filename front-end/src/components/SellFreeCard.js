import * as React from 'react';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import { useState, useEffect } from 'react';
import StarIcon from '@mui/icons-material/Star';
import { Card, CardContent, CardMedia, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from '@mui/material';
import { ethers } from 'ethers';
import { useWeb3 } from '../Web3Context';


export default function SellCard({ tokenId, image, title, petclass, attribute, description, price, orignPrice, states, alt, web3 }) {

    // 定义各种状态变量
    // const [timeLeft, setTimeLeft] = useState(calculateTimeLeft(deadline));
    const [open, setOpen] = useState(false);
    const { petMarket } = useWeb3();

    // useEffect(() => {
    //     const interval = setInterval(() => {
    //         setTimeLeft(calculateTimeLeft(deadline));
    //     }, 1000);

    //     return () => clearInterval(interval);
    // }, [deadline]);

    // 计算拍卖到期时间
    function calculateTimeLeft(endTime) {
        const difference = endTime * 1000 - new Date().getTime(); // 将时间戳转换为秒并计算差值
        let timeLeft = {};

        if (difference > 0) {
            timeLeft = {
                days: Math.floor(difference / (1000 * 60 * 60 * 24)),
                hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
                minutes: Math.floor((difference / 1000 / 60) % 60),
                seconds: Math.floor((difference / 1000) % 60)
            };
        } else {
            return { timeOut: true }; // 如果差值小于等于0，返回一个标志表示时间已经超时
        }

        return timeLeft;
    }

    // 宠物信息展示
    const InfoClickOpen = () => {
        setOpen(true);
    };

    const InfoClose = () => {
        setOpen(false);
    };

    // 用五角星代表宠物等级
    const showStars = (num) => {
        return Array.from({ length: parseInt(num) }, (_, i) => (
            <StarIcon key={i} style={{ color: '#DAA520' }} /> // 设置星为金色
        ));
    };

    // 控制购买按钮
    const buyItem = async () => {
        if (!petMarket) {
            alert("Contract not loaded, check connection to Ethereum network.");
            return;
        }
        try {
            console.log('Original Price:', orignPrice);
            const weiValue = web3.utils.toWei(orignPrice.toString(), 'ether');
            
            console.error('weiValue:', weiValue);
            const transaction = await petMarket.methods.buyPet(tokenId).send({ from: window.ethereum.selectedAddress, value: weiValue });
            console.log('Transaction successful:', transaction);
        } catch (error) {
            console.log('weiValue:', orignPrice.toString()); 
            console.error('Purchase failed:', error);
            console.log('Error object:', error); 
            alert('Purchase failed. See console for details.');
        }
    };


    function formatPrice(priceInEth) {
        if (!priceInEth || isNaN(parseFloat(priceInEth))) {
            return '0'; // 或者根据你的需求返回 'Unavailable' 或其他
        }

        const formattedPrice = parseFloat(priceInEth);
        // 如果价格小于0.00001，使用科学计数法表示
        if (formattedPrice < 0.00001) {
            return formattedPrice.toExponential(2);
        }

        // 大于或等于0.00001时，直接显示，删除末尾不必要的0
        return formattedPrice.toString().replace(/\.?0+$/, '');
    }


    return (
        <Card sx={{ maxWidth: 300 }}>
            <CardActionArea onClick={InfoClickOpen}>
                <CardMedia
                    component="img"
                    height="250"
                    image={image}
                    alt={alt}
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {title}
                    </Typography>
                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                        <Typography variant="h6" color="text.secondary" component="div">
                            {showStars(petclass)}
                        </Typography>
                        {/* {timeLeft.timeOut ? ( // 检查是否超时
                            <Typography variant="subtitle2" color="error" style={{ marginLeft: '0.5rem' }}>
                                Time Out
                            </Typography>
                        ) : (
                            <Typography variant="subtitle2" color="green" style={{ marginLeft: '0.5rem' }}>
                                {`  ${timeLeft.days}d ${timeLeft.hours}h ${timeLeft.minutes}m ${timeLeft.seconds}s`}
                            </Typography>
                        )} */}
                    </div>
                    <Typography variant="body2" color="text.secondary">
                        {description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                <Typography variant="h6" color="text.secondary" sx={{ flexGrow: 1, color: 'rgba(255, 0, 0, 0.6)' }}>
                    {formatPrice(price)}
                </Typography>

                <Button
                    size="large"
                    color="primary"
                    sx={{
                        border: '2px solid',
                        borderColor: 'primary.main',
                        padding: '8px 30px',
                        fontSize: '1rem',
                    }}
                    onClick={buyItem}
                >
                    Buy
                </Button>

            </CardActions>

            {/* 控制商品细节展示框 */}
            <Dialog
                open={open}
                onClose={InfoClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
                sx={{
                    '& .MuiDialog-paper': { // 直接目标Dialog的内部Paper组件
                        width: '70%', // 宽度为视口宽度的70%
                        height: '70%' // 最大高度为500px
                    }
                }}
            >
                <DialogTitle id="alert-dialog-title">{"Pet Details"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        <Typography gutterBottom variant="h3">
                            {title}
                        </Typography>
                        <Typography variant="h5" style={{ marginBottom: '16px', color: '#FF5733' }}>
                            Attributes: {attribute.join(", ")}
                        </Typography>
                        {/* <CardMedia
                            component="img"
                            height="70%"
                            image={image}
                            alt={alt}
                        /> */}
                        <Typography variant="h6">
                            Class: {showStars(petclass)}
                        </Typography>
                        <Typography variant="h6">
                            Description: {description}
                        </Typography>
                        {/* <Typography variant="body1">Previous bids: {prebid} ETH</Typography> */}
                        <Typography variant="h6">
                            Status: {states === '1' ? 'On Sale' : 'Sold Out'}
                        </Typography>

                        {/* 一口价模式下不再需要展示历史出价 */}
                        {/* <Typography variant="h6" style={{ marginTop: '20px' }}>
                            Previous Bids (from previous to latest):
                        </Typography>
                        {prebid.map((bid, index) => (
                            <Typography key={index} variant="h6">{`${index + 1}. ${bid} ETH`}</Typography>
                        ))} */}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={InfoClose} color="primary" autoFocus>
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </Card>

    );
}