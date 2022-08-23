import * as React from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import { styled, alpha } from '@mui/material/styles';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import moment from 'moment';
import history from '../../utils/history';
import { baseUrl } from '../../utils/urls';

const StyledMenu = styled((props) => (
    <Menu
      elevation={0}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'right',
      }}
      {...props}
    />
  ))(({ theme }) => ({
    '& .MuiPaper-root': {
      borderRadius: 6,
      marginTop: theme.spacing(1),
      minWidth: 180,
      color:
        theme.palette.mode === 'light' ? 'rgb(55, 65, 81)' : theme.palette.grey[300],
      boxShadow:
        'rgb(255, 255, 255) 0px 0px 0px 0px, rgba(0, 0, 0, 0.05) 0px 0px 0px 1px, rgba(0, 0, 0, 0.1) 0px 10px 15px -3px, rgba(0, 0, 0, 0.05) 0px 4px 6px -2px',
      '& .MuiMenu-list': {
        padding: '4px 0',
      },
      '& .MuiMenuItem-root': {
        '& .MuiSvgIcon-root': {
          fontSize: 18,
          color: theme.palette.text.secondary,
          marginRight: theme.spacing(1.5),
        },
        '&:active': {
          backgroundColor: alpha(
            theme.palette.primary.main,
            theme.palette.action.selectedOpacity,
          ),
        },
      },
    },
  }));
  
function RegisteredEventCard(props) {
  const detailsClick = () => {
    console.log("Id", props.data.id);
    history.push(`/event/id/${props.data.id}`);
  };

  return (
    <Card >
      <CardHeader
        title={props.data.event.title}
        subheader="Event Id"
      />
      <CardContent style={{paddingTop:"0px"}}>
        <Typography variant="body2">
          Location : {props.data.event.address.city}, {props.data.event.address.state}<br></br>
          Fee : {props.data.event.fees}<br></br>
          Start Date Time : {moment(props.data.event.start_date).format('yyyy-MM-DD kk:mm:ss')}<br></br>
          End Date Time : {moment(props.data.event.end_date).format('yyyy-MM-DD kk:mm:ss')}<br></br>
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Description : {props.data.event.description}
        </Typography>
      </CardContent>
      <CardActions style={{float:"right"}}>
        <Button variant="contained" onClick={detailsClick} size="large">View Details</Button>
      </CardActions>
    </Card>
  );
}

export {RegisteredEventCard};