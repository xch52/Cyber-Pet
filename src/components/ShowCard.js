import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import SellWindow from '../components/SellWindow';

export default function ShowCard({ image, title, description, price, alt }) {
    const [openFeed, setOpenFeed] = useState(false);
    const [openSell, setOpenSell] = useState(false);
  
    const handleOpenFeed = () => {
      setOpenFeed(true);
    };
  
    const handleCloseFeed = () => {
      setOpenFeed(false);
    };
  
    const handleOpenSell = () => {
      setOpenSell(true);
    };
  
    const handleCloseSell = () => {
      setOpenSell(false);
    };
  
    return (
      <Card sx={{ maxWidth: 300 }}>
        <CardActionArea>
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
            <Typography variant="body2" color="text.secondary">
              {description}
            </Typography>
          </CardContent>
        </CardActionArea>
        <CardActions sx={{ justifyContent: 'space-between' }}>
          <Button
            size="large"
            color="secondary"
            onClick={handleOpenFeed}
            sx={{
              border: '2px solid',
              borderColor: 'secondary.main',
              padding: '8px 30px',
              fontSize: '1rem',
            }}
          >
            Feed
          </Button>
          <Dialog open={openFeed} onClose={handleCloseFeed}>
            <DialogTitle>{"Feed Pet"}</DialogTitle>
            <DialogContent>
              <DialogContentText>
                {/* Your Feed dialog content here */}
                Do you want to feed your pet?
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseFeed} color="primary">
                Cancel
              </Button>
              <Button onClick={handleCloseFeed} color="primary">
                Feed
              </Button>
            </DialogActions>
          </Dialog>
  
          <Button
            size="large"
            color="primary"
            onClick={handleOpenSell}
            sx={{
              border: '2px solid',
              borderColor: 'primary.main',
              padding: '8px 30px',
              fontSize: '1rem',
            }}
          >
            Sell
          </Button>
          <Dialog open={openSell} onClose={handleCloseSell} fullWidth>
          {/* SellWindow组件被用作对话框内容 */}
          <SellWindow />
        </Dialog>
        </CardActions>
      </Card>
    );
  }