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
            color="primary"
            onClick={handleOpenSell}
            sx={{
              flexGrow: 1,  
              border: '2px solid',
              borderColor: 'primary.main',
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