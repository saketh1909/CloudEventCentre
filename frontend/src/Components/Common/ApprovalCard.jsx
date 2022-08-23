import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import axios from 'axios';
import history from '../../utils/history';
import { baseUrl } from '../../utils/urls';
import Rating from '@mui/material/Rating';

function ApprovalCard(props) {
  const [expanded, setExpanded] = React.useState(false);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const [avgRating, setAvgRating] = React.useState(0);
  let sum = 0;
  let avg = 0;
  React.useEffect(() => {
    console.log("sdsd",props.data.user.id);
    axios.get(`${baseUrl}/review/getReviewsForUser?userId=${props.data.user.id}`)
        .then(res => {
        res.data.map((s)=>{
          sum += s.rating;
        });
        setAvgRating(sum/res.data.length);
        avg = (sum/res.data.length);
        }).catch(err => {
        console.log(err.response);
        })
  },[]);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const approveClick = () => {
    console.log("Id", props.data.id);
    axios.put(`${baseUrl}/event/eventRequestApproval?participantId=${props.data.id}&approval=1`).then(res => {
      window.location.reload(false);
      // history.push(`/event/requests/${props.data.id}`);  
      console.log(res.data);
      }).catch(err => {
      console.log(err.response);
      });
    setAnchorEl(null);
  };
  const denyClick = () => {
    console.log("Id", props.data.id);
    axios.put(`${baseUrl}/event/eventRequestApproval?participantId=${props.data.id}&approval=2`).then(res => {
      window.location.reload(false);
      console.log(res.data);
      // history.push(`/event/requests/${props.data.id}`);
      }).catch(err => {
      console.log(err.response);
      });
    setAnchorEl(null);
  };
  const seeAll = () => {
    history.push(`/participant/reviews/683`);
  };

  return (
    <Card >
      <CardContent style={{paddingTop:"0px"}}>
        <Typography variant="body2">
          <p style={{marginBottom:"0px", fontSize:"25px"}}>{props.data.user.name}</p>
          {props.data.user.accountType === "P"? "[Person]": "[Organization]"} Id : {props.data.user.id}<br></br>
          Description : {props.data.user.description}<br></br>
          <Rating name="no-value" value={avgRating} precision={0.1}/><br></br>
          <a href="" onClick={seeAll}>See All Reviews</a>
        </Typography>
      </CardContent>
      <CardActions>
        <Button variant="contained" href="" onClick={() => approveClick()} size="large">Approve</Button>
        <Button variant="contained" href="" onClick={() => denyClick()} size="large">Deny</Button>
      </CardActions>
    </Card>
  );
}

export { ApprovalCard };