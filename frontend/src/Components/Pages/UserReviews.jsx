import * as React from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import { ReviewCard } from '../Common/ReviewCard';  
import history from '../../utils/history';
import Button from '@mui/material/Button';
import { baseUrl } from '../../utils/urls';
import { useParams } from 'react-router-dom';

function UserReviews(props) {
  const {participantId} = useParams();
  const [eventReviewData, setEventReviewData] = React.useState();
  React.useEffect(() => {
    axios.get(`${baseUrl}/review/getReviewsForUser?userId=${participantId}`)
      .then(res => {
      console.log(res);
      if(res.data != undefined){
        setEventReviewData(res.data);
        console.log(res.data);
      }
      }).catch(err => {
      console.log(err.response);
      })
  },[]);
  return (
  <Box sx={{ flexGrow: 1 }}>
    <Grid container spacing={2}>
      <Grid item xs={2}>
          <Button variant="contained" type="submit" onClick={(e) => history.push('/home')}  size="large">Go Back</Button>
      </Grid>
      <Grid item xs={12}>
        <h1>User Reviews</h1>
      </Grid>
      {(eventReviewData != undefined)? eventReviewData.map(a => (
        <Grid item xs={6}>
          <ReviewCard data={a}/>
        </Grid>
      )): " " }
    </Grid>
  </Box>
  );
}

export { UserReviews };