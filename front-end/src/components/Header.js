import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import Typography from '@mui/material/Typography';
import Link from '@mui/material/Link';
import HomeIcon from '../assets/HomeIcon.jpg';
import { Link as RouterLink } from 'react-router-dom';
import { useWeb3 } from '../Web3Context';

function Header(props) {
  const { sections, title } = props;
  const { account, connectWallet } = useWeb3();

  return (
    <React.Fragment>
      <Toolbar sx={{ borderBottom: 1, borderColor: 'divider', mt: 1, mb: 1 }}>

        {/* 显示网站小图标 */}
        <IconButton edge="start" color="inherit" aria-label="logo">
          <img src={HomeIcon} alt="logo" style={{ height: '50px' }} />
        </IconButton>

        {/* 显示网站名 */}
        <Typography
          component="h2"
          variant="h4"
          color="inherit"
          align="center"
          noWrap
          sx={{ flex: 1 }}
        >
          {title}
        </Typography>

        {/* 搜索按钮，目前无功能 */}
        <IconButton>
          <SearchIcon />
        </IconButton>

        {/* 连接钱包按钮 */}
        <Button variant="outlined" size="small" onClick={connectWallet}>
          {account ? `Connected: ${account.substring(0, 6)}...${account.substring(account.length - 4)}` : "Connect Wallet"}
        </Button>

      </Toolbar>

      {/* 导航栏按钮显示 */}
      <Toolbar
        component="nav"
        variant="dense"
        sx={{ justifyContent: 'space-between', overflowX: 'auto', mt: 3, mb: 3 }}
      >
        {sections.map((section) => (
          <Link
            component={RouterLink}
            to={section.url}
            color="inherit"
            noWrap
            key={section.title}
            variant="body2"
            href={section.url}
            sx={{ p: 1, flexShrink: 0, fontSize: '25px', color: 'orange' }}
          >
            {section.title}
          </Link>
        ))}
      </Toolbar>
    </React.Fragment>
  );
}

// Header.propTypes = {
//   sections: PropTypes.arrayOf(
//     PropTypes.shape({
//       title: PropTypes.string.isRequired,
//       url: PropTypes.string.isRequired,
//     }),
//   ).isRequired,
//   title: PropTypes.string.isRequired,
// };

export default Header;
