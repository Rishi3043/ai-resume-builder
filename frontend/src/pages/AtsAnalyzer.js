import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import "./PageStyles.css";

function AtsAnalyzer(){

const [result,setResult] = useState(null);
const [loading,setLoading] = useState(false);
const [resumes,setResumes] = useState([]);
const [selectedId,setSelectedId] = useState("");

const location = useLocation();

useEffect(() => {
fetchResumes();
}, []);

useEffect(() => {
const params = new URLSearchParams(location.search);
const id = params.get("resumeId");

if(id){
setSelectedId(id);
analyzeATS(id);
}
}, [location.search]);

const fetchResumes = async () => {
try{
const res = await axios.get(`${process.env.REACT_APP_API_URL}/api/resumes/user/3`);
setResumes(res.data);
}catch{
console.log("Failed to load resumes");
}
};

const analyzeATS = async (id) => {

if(!id) return;

setLoading(true);

try{
const res = await axios.get(
`${process.env.REACT_APP_API_URL}/api/resumes/${id}/analyze`
);

setResult(res.data);

}catch{
alert("ATS Failed");
}

setLoading(false);
};

return(

<div className="page">

<h1>ATS Analysis</h1>

<select value={selectedId} onChange={(e)=>setSelectedId(e.target.value)}>
<option value="">Select Resume</option>
{resumes.map(r=>(
<option key={r.id} value={r.id}>{r.fullName}</option>
))}
</select>

<button className="btn primary" onClick={()=>analyzeATS(selectedId)}>
Analyze
</button>

{loading && <p>Analyzing...</p>}

{result && (

<div className="card">

<h2>Score: {result.score || 0}%</h2>

<div className="progress">
<div className="progress-fill"
style={{width:`${result.score || 0}%`}}></div>
</div>

<h3>Strengths</h3>
<ul>
{(result.strengths || []).map((s,i)=>(
<li key={i}>{s}</li>
))}
</ul>

<h3>Improvements</h3>
<ul>
{(result.improvements || []).map((s,i)=>(
<li key={i}>{s}</li>
))}
</ul>

</div>

)}

</div>

)

}

export default AtsAnalyzer;