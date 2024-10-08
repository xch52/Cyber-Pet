import React from 'react';
import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import { makeStyles } from '@mui/styles';
import Container from '@mui/material/Container';
import { green, red  } from '@mui/material/colors';
import { useEffect, useState, useContext } from 'react';
import { useWeb3 } from '../Web3Context';


function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const useStyles = makeStyles((theme) => ({
    '@global': {
        ul: {
            margin: 0,
            padding: 0,
            listStyle: 'none',
        },
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
        backgroundColor: green[200],
    },
    cardPricing: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'baseline',
        marginBottom: theme.spacing(2),
    },
}));

const tiers_2 = [
    {
        title: 'Luxury',
        subheader: 'Most popular',
        price: '0.01',
        lotteryFee : 0.002,
        chance: '5 chances',
        amount: 5,
        description: [
            '5 chances to wish',
            '2 stars pet is promised',
            // 'Higher probability',
            // 'Lower single price',
        ],
        buttonText: 'Make a wish',
        buttonVariant: 'contained',
    },
];

const tiers_1 = [
    {
        title: 'Basic',
        subheader: 'Worth trying',
        price: '0.002',
        lotteryFee : 0.002,
        chance: '1 chance',
        amount: 1,
        description: [
            '1 chance to wish',
            'no promise',
            // 'Basic probability',
            // 'Basic single price',
        ],
        buttonText: 'Make a wish',
        buttonVariant: 'contained',
    },
];


export default function Lottery() {

    const allTiers = [...tiers_1, ...tiers_2];
    const classes = useStyles();
    const { petLottery, web3, account } = useWeb3();  // 使用 useContext 获取智能合约实例



    const makeWish = async (tier) => {
        const amount = tier.amount;  // 根据选择的套餐决定数量
        const priceWei = web3.utils.toWei((tier.lotteryFee * amount).toString(), 'ether');
        console.log("Amount: ", amount);
        console.log("lotteryFee: ", tier.lotteryFee);
        try {
            if (!petLottery) {
                console.log("Contract not loaded or wallet not connected");
                return;
            }

            await petLottery.methods.requestRandomWords(amount).send({
                from: account,
                value: priceWei
            });
            alert('Transaction submitted! Check your wallet.');
        } catch (error) {
            console.error('Error sending transaction:', error);
            alert('Transaction failed: ' + error.message);
        }
    };




    return (
        <React.Fragment>
            <CssBaseline />
            <AppBar position="static" color="default" elevation={0} className={classes.appBar}>
 
            </AppBar>
            {/* 标题 */}
            <Container maxWidth="sm" component="main" className={classes.heroContent}>
                <Typography component="h1" variant="h2" align="center" color="orange" gutterBottom>
                    Fortune Pool
                </Typography>
                <Typography variant="h5" align="center" color="textSecondary" component="p">
                Choose your favorite fortune pool, click the "Wish" button, and say in your mind the pet you want to get, 
                you have a chance to get it!
                </Typography>
            </Container>

            {/* 选项框展示*/}
            <Container maxWidth="md" component="main">
                <Grid container spacing={5} alignItems="flex-end" justifyContent="center">
                    {allTiers.map((tier, index) => (
                        <Grid item key={index} xs={12} sm={6} md={6}>
                            <Card>
                                <CardHeader
                                    title={tier.title}
                                    subheader={tier.subheader}
                                    titleTypographyProps={{ align: 'center' }}
                                    subheaderTypographyProps={{ align: 'center' }}
                                    className={classes.cardHeader}
                                />
                                <CardContent>
                                    <div className={classes.cardPricing}>
                                        <Typography component="h2" variant="h3" color="textPrimary">
                                            {tier.price} ETH
                                        </Typography>
                                        <Typography variant="h6" color="textSecondary">
                                             /{tier.chance} 
                                        </Typography>
                                    </div>
                                    <ul>
                                        {tier.description.map((line) => (
                                            <Typography component="li" variant="subtitle1" align="center" key={line} color={red}>
                                                {line}
                                            </Typography>
                                        ))}
                                    </ul>
                                </CardContent>
                                <CardActions>
                                    <Button fullWidth variant={tier.buttonVariant} color="primary"  onClick={() => makeWish(tier)}>
                                        {tier.buttonText}
                                    </Button>
                                </CardActions>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </Container>
        </React.Fragment>
    );
}