import React, { useRef } from "react";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
import "./ResumePreview.css";

function ResumePreview({
fullName,
title,
skills,
experience,
linkedin,
github,
location
}){

const resumeRef = useRef();

const downloadPDF = async () => {

const element = resumeRef.current;

const canvas = await html2canvas(element);

const imgData = canvas.toDataURL("image/png");

const pdf = new jsPDF("p","mm","a4");

const imgWidth = 210;
const pageHeight = 295;
const imgHeight = canvas.height * imgWidth / canvas.width;

pdf.addImage(imgData,"PNG",0,0,imgWidth,imgHeight);

pdf.save("resume.pdf");

};

return(

<div>

<div ref={resumeRef} className="resume-preview">

<div className="left-column">

<h2>{fullName || "Your Name"}</h2>
<p>{title || "Your Role"}</p>

<hr/>

<h3>Skills</h3>
<p>{skills || "Skills will appear here"}</p>

<h3>Contact</h3>
<p>{location}</p>
<p>{linkedin}</p>
<p>{github}</p>

</div>

<div className="right-column">

<h1>Professional Experience</h1>

<p>{experience || "Your experience will appear here"}</p>

</div>

</div>

<button onClick={downloadPDF} style={{
marginTop:"20px",
padding:"10px 20px",
background:"#22c55e",
border:"none",
borderRadius:"6px",
color:"white",
cursor:"pointer"
}}>

Download Resume PDF

</button>

</div>

)

}

export default ResumePreview;