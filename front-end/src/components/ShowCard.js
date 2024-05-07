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
import StarIcon from '@mui/icons-material/Star';


export default function ShowCard({ id, image, title, petclass, attribute, description, alt }) {
  
  const [openFeed, setOpenFeed] = useState(false);
  const [openSell, setOpenSell] = useState(false);
  const [open, setOpen] = useState(false);

  // 点击图片展示宠物详细信息
  const InfoClickOpen = () => {
    setOpen(true);
  };

  const InfoClose = () => {
    setOpen(false);
  };

  
  // Sell 窗口的打开与关闭
  const clickOpenSell = () => {
    setOpenSell(true);
  };

  const clickCloseSell = () => {
    setOpenSell(false);
  };

  // 用五角星代表宠物等级
  const showStars = (num) => {
    return Array.from({ length: parseInt(num) }, (_, i) => (
      <StarIcon key={i} style={{ color: '#DAA520' }} /> // 设置星为金色
    ));
  };


  return (
    <Card sx={{ maxWidth: 300 }}>

      {/* 背包中拥有的宠物信息展示 */}
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
          </div>
          <Typography variant="body2" color="text.secondary">
            {description}
          </Typography>
        </CardContent>
      </CardActionArea>

      {/* 售卖按钮 */}
      <CardActions sx={{ justifyContent: 'space-between' }}>
        <Button
          size="large"
          color="primary"
          onClick={clickOpenSell}
          sx={{
            flexGrow: 1,
            border: '2px solid',
            borderColor: 'primary.main',
            fontSize: '1rem',
          }}
        >
          Sell
        </Button>
        <Dialog open={openSell} onClose={clickCloseSell} fullWidth>

          {/* SellWindow组件被用作对话框内容 */}
          <SellWindow
            id={id}
          />

        </Dialog>

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
              {/* <Typography variant="h6" style={{ marginTop: '20px' }}>
                Previous Bids (from previous to latest):
              </Typography> */}

              {/* 暂时不用展示历史出价 */}
              {/* {prebid.map((bid, index) => (
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


      </CardActions>
    </Card>
  );
}