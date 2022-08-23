import * as React from 'react';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import axios from 'axios';
import moment from 'moment';
import Modal from '@mui/material/Modal';
import history from '../../utils/history';
import Rating from '@mui/material/Rating';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import { baseUrl } from '../../utils/urls';
import { useParams } from 'react-router-dom';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

const styleModal = {
    margin : "10px",
    width : "80%",

  };

function ApprovedParticipants(props) {
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const accountType = loggedUser.accountType;
  const [reviewValue, setReviewValue] = React.useState(0);
  const [reviewText, setReviewText] = React.useState();
  const {eventId} = useParams();
  let currDate = moment();
  currDate = moment(currDate._d).format('yyyy-MM-DD kk:mm:ss');
  const [eventData, setEventData] = React.useState();
  console.log(currDate);
  console.log(props);
  React.useEffect(() => {
    axios.get(`${baseUrl}/event/getEvent?eventId=${eventId}`)
      .then(res => {
      console.log(res.data);
      if(res.data != undefined){
        setEventData(res.data);
        
      }
      }).catch(err => {
      console.log(err.response);
      })
  },[]);
  const seeAll = () => {
    history.push(`/participant/reviews/683`);
  };
  const [open, setOpen] = React.useState(false);
  const handleOpen = (id) => {
    setOpen(true);
    setCurrModalUserId(id);
  }
  const [currModalUserId, setCurrModalUserId] = React.useState(false);
  const handleClose = () => setOpen(false);
  const handlePostClose = (e,formRef) => {
    if(loggedUserId.length===0 || currModalUserId.length===0 || eventId.length===0 || reviewValue.length===0 || reviewText.length===0){
            return;
        }
    e.preventDefault();
    axios.post(`${baseUrl}/review/createReview?reviewerId=${loggedUserId}&revieweeId=${currModalUserId}&eventId=${eventId}&rating=${reviewValue}&reviewComment=${reviewText}`)
    .then(res => {
    console.log(res);
    setOpen(false)
    history.push('/home');
    }).catch(err => {
    console.log(err.response);
    })
};
  return(
    <Box sx={{ flexGrow: 1 }}>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <h1>Add Review</h1>
          <Rating name="no-value" onChange={(e)=>setReviewValue(e.target.value)} value={reviewValue} />
          <TextField
              id="outlined-uncontrolled"
              label="Answer"
              style={{...styleModal}}
              onChange={(e) => setReviewText(e.target.value)}
              value={reviewText}
          />
          <Button 
          variant="contained" 
          onClick={handlePostClose}  
          size="large" 
          style={{marginBottom:"20px 0px 40px 0px"}}>Post Review</Button>
        </Box>
      </Modal>
      <Grid container spacing={2}>
        <Grid item xs={2}>
            <Button variant="contained" type="submit" onClick={(e) => history.push('/home')}  size="large">Go Back</Button>
        </Grid>
        <Grid item xs={8}>
        </Grid>
        <Grid item xs={2}>
        </Grid>
        <Grid item xs={8}>
            <h1 style={{margin:"0px"}}>Event Participants</h1>
        </Grid>
        <Grid item xs={4}>
        </Grid>
        {(eventData != undefined)? eventData.participants.map(ed => ( 
        <Grid item xs={12}>
        <Card >
            <CardHeader
                title={ed.user.name}
                subheader={ed.user.screenName}
            />
            <CardContent style={{paddingTop:"0px"}}>
                <Typography variant="body2">
                Gender: {ed.user.gender}<br></br>
                Location: {ed.user.address.city}, {ed.user.address.state}<br></br>
                </Typography>
            </CardContent>
            <CardActions style={{float:"right"}}>
              <Button variant="contained" onClick={()=>handleOpen(ed.user.id)} size="large">Add Review</Button>
                {/* <Button variant="contained" size="large">Check Profile</Button> */}
                <Button variant="contained" onClick={()=>{history.push(`/participant/reviews/${ed.user.id}`);}}size="large">See All Reviews</Button>
            </CardActions>
        </Card>
        </Grid>
        )): " " }
      </Grid>
    </Box>
  );
}

export { ApprovedParticipants };
