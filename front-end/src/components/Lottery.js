import React from 'react';
import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import StarIcon from '@mui/icons-material/StarBorder';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import { makeStyles } from '@mui/styles';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import { green  } from '@mui/material/colors';


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

const tiers_1 = [
    {
        title: 'Luxury',
        subheader: 'Most popular',
        price: '5',
        chance: '10 chances',
        description: [
            '10 chances to wish',
            '2 starts pet is promised',
            'Higher probability',
            'Lower single price',
        ],
        buttonText: 'Make a wish',
        buttonVariant: 'contained',
    },
];

const tiers_2 = [
    {
        title: 'Basic',
        subheader: 'Worth trying',
        price: '0.6',
        chance: '1 chance',
        description: [
            '1 chance to wish',
            'no promise',
            'Basic probability',
            'Basic single price',
        ],
        buttonText: 'Make a wish',
        buttonVariant: 'contained',
    },
];


export default function Lottery() {
    const classes = useStyles();
    const allTiers = [...tiers_1, ...tiers_2];

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
                                            <Typography component="li" variant="subtitle1" align="center" key={line}>
                                                {line}
                                            </Typography>
                                        ))}
                                    </ul>
                                </CardContent>
                                <CardActions>
                                    <Button fullWidth variant={tier.buttonVariant} color="primary">
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