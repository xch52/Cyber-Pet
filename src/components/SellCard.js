import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';


export default function SellCard({ image, title, petclass, description, price, alt }) {

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
                    <Typography gutterBottom variant="h6" color="text.secondary" component="div">
                        {petclass}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                <Typography variant="h6" color="text.secondary" sx={{ flexGrow: 1, color: 'rgba(255, 0, 0, 0.6)' }}>
                    {price}
                </Typography>
                <Button
                    size="large"
                    color="primary"
                    sx={{
                        border: '2px solid', // 设置边框的大小和样式
                        borderColor: 'primary.main', // 边框颜色，使用主题中的颜色
                        padding: '8px 30px', // 增加按钮的内边距来使按钮变大
                        fontSize: '1rem', // 可以调整字体大小来进一步改变按钮的大小感
                    }}
                >
                    Buy
                </Button>
            </CardActions>
        </Card>

    );
}