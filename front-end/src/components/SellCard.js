import * as React from 'react';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import { useState, useEffect } from 'react';
import StarIcon from '@mui/icons-material/Star';
import { Card, CardContent, CardMedia, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from '@mui/material';
import { useWeb3 } from '../Web3Context';


export default function SellCard({ image, title, petclass, attribute, description, price, prebid, states, deadline, alt, tokenId, petAuction, web3 }) {

    // 定义各种状态变量
    //const { petAuction, web3, account } = useWeb3();
    const { account } = useWeb3();
    const [timeLeft, setTimeLeft] = useState(calculateTimeLeft(deadline));
    const [open, setOpen] = useState(false);
    const [bidOpen, setBidOpen] = useState(false);
    const [bidAmount, setBidAmount] = useState('');
    const [bidError, setBidError] = useState('');

    useEffect(() => {
        const interval = setInterval(() => {
            setTimeLeft(calculateTimeLeft(deadline));
        }, 1000);

        return () => clearInterval(interval);
    }, [deadline]);

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

    // 点击图片展示宠物详细信息
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

    // 宠物出价按钮
    const bidClick = () => {
        setBidOpen(true);
    };

    const bidClose = () => {
        setBidOpen(false);
    };

    
// SellCard组件中的出价提交函数
const bidSubmit = async () => {
    const bidValueEth = parseFloat(bidAmount); // 从输入框获取用户输入的ETH数
    if (isNaN(bidValueEth) || bidValueEth <= 0) {
        setBidError('Please enter a valid bid.');
        return;
    }

    if (bidValueEth <= parseFloat(price)) {
        setBidError('Your bid must be higher than the current bid.');
        return;
    }

    console.log("Bid origin price", bidAmount);

    const bidValueWei = web3.utils.toWei(bidAmount, 'ether'); // 将ETH转换为Wei

    try {
        if (petAuction && account) {
            // 调用智能合约的bid方法，发送交易
            await petAuction.methods.bid(tokenId).send({ from: account, value: bidValueWei });
            console.log("Bid successfully：", bidValueEth, "ETH");
            bidClose(); // 关闭出价对话框
            setBidAmount(''); // 清空输入框
            setBidError(''); // 清除错误信息
        } else {
            setBidError('The contract is not loaded or the wallet is not connected.'); // 合约未加载或钱包未连接
        }
    } catch (error) {
        console.error("Bid failed：", error);
        console.log("Bid account：", account);
        console.log("tokenId:", account);
        console.log("Bid bidValueWei:", bidValueWei);
        console.log("Bid price:", price);
        console.log("petAuction address:", petAuction);
        setBidError('Bid failed, please ensure the bid is higher than the current maximum bid and that the auction has not yet ended.');
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
        <Card sx={{ maxWidth: 300, height: 470 }}>
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

                        <Typography variant="h7" color="text.secondary">
                            End: {deadline} {/* 直接显示传入的时间字符串 */}
                        </Typography>

                    </div>
                    <Typography variant="body2" color="text.secondary" style={{ height: 40, overflow: 'hidden' }}> {/* 限制描述高度并隐藏溢出内容 */}
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
                    onClick={bidClick}
                >
                    Bid
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
                        {/* <Typography variant="h6">
                            Status: {states === '1' ? 'On Sale' : 'Sold Out'}
                        </Typography> */}
                        <Typography variant="h6" style={{ marginTop: '20px' }}>
                            Previous Bids (from previous to latest):
                        </Typography>
                        {prebid.map((bid, index) => (
                            <Typography key={index} variant="h6">{`${index + 1}. ${bid} ETH`}</Typography>
                        ))}
                    </DialogContentText>
                </DialogContent>

                <DialogActions>
                    <Button onClick={InfoClose} color="primary" autoFocus>
                        Close
                    </Button>
                </DialogActions>
            </Dialog>

            {/* 控制出价框 */}
            <Dialog
                open={bidOpen}
                onClose={bidClose}
            >
                <DialogTitle>Enter Your Bid</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Please enter your bid amount for this pet.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        label="Bid Amount (ETH)"
                        type="text"
                        fullWidth
                        variant="standard"
                        value={bidAmount}
                        onChange={e => setBidAmount(e.target.value)}
                        error={!!bidError}
                        helperText={bidError}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={bidClose} color="primary" variant="outlined" sx={{ margin: '8px' }}>
                        Cancel
                    </Button>
                    <Button onClick={bidSubmit} color="primary" variant="outlined" sx={{ margin: '8px' }}>
                        Submit
                    </Button>
                </DialogActions>
            </Dialog>
        </Card>

    );
}