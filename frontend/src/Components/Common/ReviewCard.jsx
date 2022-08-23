import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Rating from '@mui/material/Rating';
import axios from 'axios';
  
function ReviewCard(props) {

  return (
    <Card >
      <CardContent style={{paddingTop:"0px"}}>
        <Typography variant="body2">
          <p style={{marginBottom:"0px", fontSize:"25px"}}>{props.data.reviewer.name}</p>
          <Rating name="no-value" value={props.data.rating} precision={0.1}/><br></br>
          Review Description : {props.data.reviewComment} <br></br>
        </Typography>
      </CardContent>
    </Card>
  );
}

export { ReviewCard };