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
import firebase from '../../utils/firebase';
import history from '../../utils/history';
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

function ParticipantForum() {
  const {eventId} = useParams();
  let loggedUser = localStorage.getItem("userId");
  if(loggedUser == undefined || loggedUser === null){
    history.push("/login");
  }
  loggedUser = JSON.parse(loggedUser);
  const loggedUserId = loggedUser.id;
  const accountType = loggedUser.accountType;
  const [forum, setforum] = React.useState();
  const [image , setImage] = useState('');
  const [eventData, setEventData] = React.useState();
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
    axios.get(`${baseUrl}/eventForum/getForumQuestionAndAnswer?eventId=${eventId}&forumType=2`)
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
  const handleOpenOrgReview = () => setOpenReview(true);
  const handlecloseOutOrgReview = () => setOpenReview(false);

  const [modalButtonQue, setModalButtonQue] = React.useState(false);

  const handleOpen = (id) => {
    setOpen(true)
    setModalButtonQue(id);
  };
  const handleClose = () => {
    setOpen(false)
  };
  const handleAnswerClose = async () => {
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
    axios.post(`${baseUrl}/eventForum/createForumQuestion?eventId=${eventId}&userId=${loggedUserId}&question=${question}?&forumType=2`).then(res => {
      window.location.reload(false);
      console.log(res.data);
      }).catch(err => {
      console.log(err.response);
      });
  };
  const handleCloseForum = () => {
    setOpenQue(false)
    axios.post(`${baseUrl}eventForum/closeParticipantForum?eventId=${eventId}`).then(res => {
      history.push("/home");
      console.log(res.data);
      }).catch(err => {
      console.log(err.response);
      });
  };
  const handleCloseOrgReview = () => {
    if(loggedUserId.length===0 || eventId.length===0 || reviewValue.length===0 || reviewText.length===0){
      return;
    }
    axios.post(`${baseUrl}/review/createReview?reviewerId=${loggedUserId}&revieweeId=${eventData.userId}&eventId=${eventId}&rating=${reviewValue}&reviewComment=${reviewText}`)
    .then(res => {
    console.log(res);
    setOpen(false)
    history.push('/home');
    }).catch(err => {
    console.log(err.response);
    })
    setOpenReview(false)

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
              { accountType === "O" ? <>
              <Button variant="contained" type="submit" onClick={handleCloseForum}  size="large">Close Participant forum</Button>
              </> :null }
                
              </Grid>
          {(eventData != undefined)? 
          <>
            <Grid item xs={8}>
              <h1>Forum for {eventData.title}</h1>
              { eventData.status === 1 ? <><Chip label="Cancelled" style={{background:"red"}}/> - { eventData.forumCloseReason !== null ?  eventData.forumCloseReason : null }<br></br></> : null }
              { eventData.status === 0 ? <><Chip label="Open for registration"/><br></br></> : null }
              { eventData.status === 2 ? <><Chip label="Active"/><br></br></> : null }
              { eventData.status === 3 ? <><Chip label="Past"/><br></br></> : null }
              { eventData.participantsCount===eventData.maxParticipants ? <><Chip label="Full"/><br></br></> : null }
            </Grid>
            <Grid item xs={9}>
              <Button variant="contained" onClick={handleOpenOrgReview} disabled={eventData.status !== 0 || accountType === "O"}  size="large" style={{marginBottom:"20px 0px 40px 0px"}}>Add Organizer Review</Button>
              <Modal
                open={openReview}
                onClose={handlecloseOutOrgReview}
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
                  onClick={handleCloseOrgReview}  
                  size="large" 
                  style={{marginBottom:"20px 0px 40px 0px"}}>Post Review</Button>
                </Box>
              </Modal>
            </Grid>
            <Grid item xs={3}>
              <Button variant="contained" onClick={handleOpenQue} disabled={eventData.status !== 0 && eventData.status !== 2} size="large" style={{marginBottom:"20px 0px 40px 0px"}}>+ Add a Message</Button>
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
                    <br></br>
                    </>
                   )): " " }
                    <Button variant="contained" disabled={eventData.status !== 0 && eventData.status !== 2} onClick={()=>handleOpen(f.id)} size="large" style={{margin:"10px 5px "}}>Reply</Button>
                    
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
                  <h1>Write an Answer</h1>
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
                      style={{marginBottom:"20px 0px 40px 0px"}}>Post</Button>
                  </Box>
              </Modal>
            </Grid>
            </>
          : " " }
          </Grid>
        </div>
  );
}

export { ParticipantForum };
