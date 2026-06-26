import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import "./PageStyles.css";

function JobMatch(){

const [data,setData] = useState(null);
const [loading,setLoading] = useState(false);
const [resumes,setResumes] = useState([]);
const [selectedId,setSelectedId] = useState("");
const [jd,setJd] = useState("");

const location = useLocation();

useEffect(()=>{
fetchResumes();
},[]);

useEffect(()=>{
const params = new URLSearchParams(location.search);
const id = params.get("resumeId");

if(id){
setSelectedId(id);
}
},[location.search]);

const fetchResumes = async ()=>{
const res = await axios.get(`${process.env.REACT_APP_API_URL}/api/resumes/user/1`);
setResumes(res.data);
};

const analyzeMatch = async ()=>{

if(!selectedId || !jd){
alert("Select resume + enter job description");
return;
}

setLoading(true);

const res = await axios.post(
`${process.env.REACT_APP_API_URL}/api/resumes/${selectedId}/match`,
jd,
{headers:{"Content-Type":"text/plain"}}
);

setData(res.data);
setLoading(false);
};

return(

<div className="page">

<h1>Job Match</h1>

<select value={selectedId} onChange={(e)=>setSelectedId(e.target.value)}>
<option value="">Select Resume</option>
{resumes.map(r=>(
<option key={r.id} value={r.id}>{r.fullName}</option>
))}
</select>

<textarea
placeholder="Paste Job Description..."
value={jd}
onChange={(e)=>setJd(e.target.value)}
/>

<button className="btn primary" onClick={analyzeMatch}>
Analyze Match
</button>

{loading && <p>Analyzing...</p>}

{data && (

<div className="card">

<h2>Match Score: {data.matchScore || 0}%</h2>

<div className="progress">
<div className="progress-fill"
style={{width:`${data.matchScore || 0}%`}}></div>
</div>

<h3>Matched Skills</h3>
{(data.matchedSkills || []).map((s,i)=>(
<span key={i} className="badge green">{s}</span>
))}

<h3>Missing Skills</h3>
{(data.missingSkills || []).map((s,i)=>(
<span key={i} className="badge red">{s}</span>
))}

</div>

)}

</div>

)

}

export default JobMatch;