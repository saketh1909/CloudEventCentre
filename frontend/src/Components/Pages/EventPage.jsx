import * as React from 'react';
import Grid from '@mui/material/Grid';
import { styled } from '@mui/material/styles';
import {useState} from 'react';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import ArrowForwardIosSharpIcon from '@mui/icons-material/ArrowForwardIosSharp';
import MuiAccordion from '@mui/material/Accordion';
import MuiAccordionSummary from '@mui/material/AccordionSummary';
import MuiAccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ReplyIcon from '@mui/icons-material/Reply';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import AssistantIcon from '@mui/icons-material/Assistant';
import {useParams} from 'react-router-dom';
import axios from 'axios';
import moment from 'moment';
import history from "../../utils/history";
import firebase from '../../utils/firebase';
import Chip from '@mui/material/Chip';
import { baseUrl } from '../../utils/urls';
import Rating from '@mui/material/Rating';

const Accordion = styled((props) => (
  <MuiAccordion disableGutters elevation={0} square {...props} />
))(({ theme }) => ({
  border: `1px solid ${theme.palette.divider}`,
  '&:not(:last-child)': {
    borderBottom: 0,
  },
  '&:before': {
    display: 'none',
  },
}));

const AccordionSummary = styled((props) => (
  <MuiAccordionSummary
    expandIcon={<ArrowForwardIosSharpIcon sx={{ fontSize: '0.9rem' }} />}
    {...props}
  />
))(({ theme }) => ({
  backgroundColor:
    theme.palette.mode === 'dark'
      ? 'rgba(255, 255, 255, .05)'
      : 'rgba(0, 0, 0, .03)',
  flexDirection: 'row-reverse',
  '& .MuiAccordionSummary-expandIconWrapper.Mui-expanded': {
    transform: 'rotate(90deg)',
  },
  '& .MuiAccordionSummary-content': {
    marginLeft: theme.spacing(1),
  },
}));

const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
  padding: theme.spacing(2),
  borderTop: '1px solid rgba(0, 0, 0, .125)',
}));

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

const subheadStyle = {
  fontSize:"20px",
  margin:"10px"
};

function EventPage() {
    const {eventId} = useParams();
    let loggedUser = localStorage.getItem("userId");
    if(loggedUser == undefined || loggedUser === null){
      history.push("/login");
    }
    let loggedUserId = localStorage.getItem("userId");
    loggedUserId = JSON.parse(loggedUserId);
    console.log(loggedUserId.id);
    loggedUserId = loggedUserId.id;
    const [forum, setforum] = React.useState();
    const [eventData, setEventData] = React.useState();
    const [avgRating, setAvgRating] = React.useState(0);
    let sum = 0;
    let avg = 0;
    React.useEffect(() => {
      axios.get(`${baseUrl}/event/getEvent?eventId=${eventId}`)
        .then(res => {
        console.log(res.data.user);
        if(res.data != undefined){
          setEventData(res.data); 
            axios.get(`${baseUrl}/review/getReviewsForUser?userId=${res.data.user.id}`)
            .then(res => {
            res.data.map((s)=>{
              sum += s.rating;
            });
            setAvgRating(sum/res.data.length);
            avg = (sum/res.data.length);
            console.log(avg);
            }).catch(err => {
            console.log(err.response);
            })
        }
        }).catch(err => {
        console.log(err.response);
        })
      axios.get(`${baseUrl}/eventForum/getForumQuestionAndAnswer?eventId=${eventId}&forumType=1`)
          .then(res => {
          console.log(res.data);
          if(res.data != undefined){
            setforum(res.data);
          }
          }).catch(err => {
          console.log(err.response);
          })
    },[]);
    const [question, setQuestion] = React.useState("");
    const [answer, setAnswer] = React.useState("");
    const [expanded, setExpanded] = React.useState();
    const [open, setOpen] = React.useState(false);
    const [openQue, setOpenQue] = React.useState(false);
    const [openReview, setOpenReview] = React.useState(false);
    const [reviewValue, setReviewValue] = React.useState(0);
    const [reviewText, setReviewText] = React.useState();


    const [modalButtonQue, setModalButtonQue] = React.useState(false);
    const [image , setImage] = useState('');
    const [imageURL , setImageURL] = useState('');

    const handleOpen = (id) => {
      setOpen(true)
      setModalButtonQue(id);
    };
    const handleClose = () => {
      setOpen(false)
    };
    const handleAnswerClose = async() => {
      setOpen(false)
      let id = Math.floor(100000 + Math.random() * 900000);
      const imagesRef=firebase.storage().ref(id.toString());
      await imagesRef.put(image);
      let url = await imagesRef.getDownloadURL();
      axios.post(`${baseUrl}/eventForum/createForumAnswer?questionId=${modalButtonQue}&userId=${loggedUserId}&answer=${answer}&imageURL=${url}`).then(res => {
        window.location.reload(false);
        console.log(res.data);
        }).catch(err => {
        console.log(err.response);
        });
    };
    const handleOpenQue = () => setOpenQue(true);
    const handlecloseOutQue = () => setOpenQue(false);
    const handleCloseQue = () => {
      setOpenQue(false)
      axios.post(`${baseUrl}/eventForum/createForumQuestion?eventId=${eventId}&userId=${loggedUserId}&question=${question}?&forumType=1`).then(res => {
        window.location.reload(false);
        console.log(res.data);
        }).catch(err => {
        console.log(err.response);
        });
    };


    const handleChange = (panel) => (event, newExpanded) => {
      setExpanded(newExpanded ? panel : false);
    };
  return(
        <div>
          <Grid container spacing={2} style={{padding :"0px 50px 0px 50px", marginBottom:"50px"}}>
            <Grid item xs={2}>
                <Button variant="contained" type="submit" onClick={(e) => history.push('/home')}  size="large">Go Back</Button>
            </Grid>
            <Grid item xs={8}>
            </Grid>
            <Grid item xs={2}>
            </Grid>
          {(eventData != undefined)? 
          <>
            <Grid item xs={9}>
              <h1>{eventData.title}</h1>
              { eventData.status === 1 ? <><Chip label="Cancelled" style={{background:"red"}}/> - { eventData.forumCloseReason !== null ?  eventData.forumCloseReason : null }<br></br></> : null }
              { eventData.status === 0 ? <><Chip label="Open for registration"/><br></br></> : null }
              { eventData.status === 2 ? <><Chip label="Active"/><br></br></> : null }
              { eventData.status === 3 ? <><Chip label="Past"/><br></br></> : null }
              { eventData.participantsCount===eventData.maxParticipants ? <><Chip label="Full"/><br></br></> : null }
            </Grid>
            <Grid item xs={3}>
            {/* <Button variant="contained" size="large" onClick={()=>history.push(`/event/forum/${eventId}`)} style={{margin:"15px 0px 40px 0px"}}>Event Forum</Button> */}
            </Grid>
            <Grid item xs={12}>
              <p style={{...subheadStyle}}> <strong> Description: </strong> :{eventData.description}  </p>
              <p style={{...subheadStyle}}> <strong> Location  </strong> : {eventData.address.city}, {eventData.address.state}</p>
              <p style={{...subheadStyle}}> <strong> Start Time & Date  </strong> : {moment(eventData.start_date).format('yyyy-MM-DD kk:mm:ss')} </p>
              <p style={{...subheadStyle}}> <strong> ead  </strong> : {moment(eventData.end_date).format('yyyy-MM-DD kk:mm:ss')}</p>
              <p style={{...subheadStyle}}> <strong> Deadline  </strong> : {moment(eventData.deadline).format('yyyy-MM-DD kk:mm:ss')}</p>
              <p style={{...subheadStyle}}> <strong> Organizer Name  </strong> : {eventData.user.name} <Rating name="no-value" value={avgRating} precision={0.1}/>
              <a href="" onClick={()=>{history.push(`/participant/reviews/${eventData.user.id}`);}}> See All Reviews</a></p>
              <p style={{...subheadStyle}}> <strong> Event Fees  </strong> : {eventData.fees}</p>
              <p style={{...subheadStyle}}> <strong> Terms & Conditions  </strong> : {eventData.policy}</p>
            </Grid>
            <Grid item xs={9}>

            </Grid>
            <Grid item xs={3}>
              <Button variant="contained" onClick={handleOpenQue} disabled={eventData.status !== 0} size="large" style={{marginBottom:"20px 0px 40px 0px"}}>+ Add a Message</Button>
              <Modal
                  open={openQue}
                  onClose={handlecloseOutQue}
                  aria-labelledby="parent-modal-title"
                  aria-describedby="parent-modal-description"
                  >
                      <Box sx={{ ...style, width: 400 , textAlign:"center"}}>
                      <h1>Add a Message</h1>
                          <TextField
                              id="outlined-uncontrolled"
                              label="Question"
                              style={{...styleModal}}
                              onChange={(e) => setQuestion(e.target.value)}
                              value={question}
                          />
                          <Button variant="contained" onClick={handleCloseQue}  size="large" style={{marginBottom:"20px 0px 40px 0px"}}>Post</Button>
                      </Box>
                  </Modal>
            </Grid>
            <Grid item xs={12}>
              

            {(forum != undefined)? forum.map(f => (
              <Accordion expanded={expanded === `panel${f.id}`} onChange={handleChange(`panel${f.id}`)} key={f.id}>
                <AccordionSummary aria-controls="panel1d-content" id="panel1d-header">
                  <Typography>{f.question}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Typography>
                  {(f.answers != undefined)? f.answers.map(a => (
                    <>
                    <ReplyIcon key={a.id}/> {(a.user===null)? "":(a.user.id===eventData.userId)?<AssistantIcon/>:""}
                    {a.answer}<br></br>
                    {(a.imageURL!=null)? <img src={a.imageURL} alt="" width="100" height="100"></img> :""}
                    {/* <img src={a.imageurl} alt="Girl in a jacket" width="500" height="600"></img> */}
                    
                    <br></br>
                    </>
                   )): " " }
                    <Button variant="contained" disabled={eventData.status !== 0} onClick={()=>handleOpen(f.id)} size="large" style={{margin:"10px 5px "}}>Reply</Button>
                    
                  </Typography>
                </AccordionDetails>
              </Accordion>
            )): " " }
            <Modal
              open={open}
              onClose={handleClose}
              aria-labelledby="parent-modal-title"
              aria-describedby="parent-modal-description"
              >
                  <Box sx={{ ...style, width: 400 , textAlign:"center"}}>
                  <h1>Question #1</h1>
                      <TextField
                          id="outlined-uncontrolled"
                          label="Answer"
                          style={{...styleModal}}
                          onChange={(e) => setAnswer(e.target.value)}
                          value={answer}
                      />
                      <TextField 
                        type="file" 
                        label="Image"
                        onChange={(e)=>{setImage(e.target.files[0])}}
                      />
                      <Button 
                      variant="contained" 
                      onClick={handleAnswerClose}  
                      size="large" 
                      style={{marginBottom:"20px 0px 40px 0px"}}>Reply</Button>
                  </Box>
              </Modal>
            </Grid>
            </>
          : " " }
          </Grid>
        </div>
  );
}

export { EventPage };
