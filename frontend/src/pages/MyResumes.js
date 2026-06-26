import React, { useEffect, useState } from "react";
import axios from "axios";
import "./PageStyles.css";
import { useNavigate } from "react-router-dom";

function MyResumes(){

const USER_ID = 1;

const [resumes,setResumes] = useState([]);
const [editingResume,setEditingResume] = useState(null);

const navigate = useNavigate();

useEffect(()=>{
fetchResumes();
},[]);

const fetchResumes = async () => {
try{
const res = await axios.get(`${process.env.REACT_APP_API_URL}/api/resumes/user/${USER_ID}`);
setResumes(res.data);
}catch{
console.log("Error loading resumes");
}
};

const startEdit = (resume) => {
setEditingResume({...resume});
};

const handleChange = (e) => {
setEditingResume({
...editingResume,
[e.target.name]: e.target.value
});
};

const updateResume = async () => {
try{
await axios.put(
`${process.env.REACT_APP_API_URL}/api/resumes/${editingResume.id}`,
editingResume
);
alert("Updated ✅");
setEditingResume(null);
fetchResumes();
}catch{
alert("Update failed");
}
};

const deleteResume = async (id) => {
if(!window.confirm("Delete this resume?")) return;

try{
await axios.delete(`${process.env.REACT_APP_API_URL}/api/resumes/${id}`);
fetchResumes();
}catch{
alert("Delete failed");
}
};

const downloadResume = (id) => {
window.open(`${process.env.REACT_APP_API_URL}/api/resumes/${id}/download`);
};

// ✅ ATS FIX
const analyzeATS = (id) => {
navigate(`/ats?resumeId=${id}`);
};

// ✅ JOB MATCH WITH RESULT PAGE
const jobMatch = (id) => {
navigate(`/match?resumeId=${id}`);
};

return(

<div className="page">

<h1>My Resumes</h1>

{editingResume ? (

<div className="card">

<h2>Edit Resume</h2>

<input name="fullName" value={editingResume.fullName} onChange={handleChange}/>
<input name="title" value={editingResume.title} onChange={handleChange}/>
<input name="email" value={editingResume.email} onChange={handleChange}/>
<input name="phone" value={editingResume.phone} onChange={handleChange}/>

<textarea name="skills" value={editingResume.skills} onChange={handleChange}/>
<textarea name="summary" value={editingResume.summary} onChange={handleChange}/>

<div className="btn-row">
<button className="btn primary" onClick={updateResume}>Save</button>
<button className="btn" onClick={()=>setEditingResume(null)}>Cancel</button>
</div>

</div>

) : (

<div className="grid">

{resumes.map(resume => (

<div key={resume.id} className="card">

<h3>{resume.fullName}</h3>
<p className="role">{resume.title}</p>

<p className="skills">{resume.skills}</p>

<div className="btn-row">

<button className="btn" onClick={()=>startEdit(resume)}>Edit</button>

<button className="btn danger" onClick={()=>deleteResume(resume.id)}>
Delete
</button>

<button className="btn success" onClick={()=>downloadResume(resume.id)}>
PDF
</button>

<button className="btn warning" onClick={()=>analyzeATS(resume.id)}>
ATS
</button>

<button className="btn purple" onClick={()=>jobMatch(resume.id)}>
Match
</button>

</div>

</div>

))}

</div>

)}

</div>

)

}

export default MyResumes;