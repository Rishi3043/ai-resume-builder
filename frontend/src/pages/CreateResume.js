import React, { useState } from "react";
import axios from "axios";
import "./PageStyles.css";

function CreateResume() {

  const [resume, setResume] = useState({
    fullName: "",
    email: "",
    phone: "",
    location: "",
    linkedin: "",
    github: "",
    skills: "",
    summary: "",
    template: "modern"
  });

  const [role, setRole] = useState("");
  const [experience, setExperience] = useState("");
  const [loadingAI, setLoadingAI] = useState(false);

  // ✅ IMAGE STATE
  const [image, setImage] = useState(null);

  // =========================
  // INPUT HANDLER
  // =========================
  const handleChange = (e) => {
    const { name, value } = e.target;

    setResume((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  // =========================
  // IMAGE HANDLER
  // =========================
  const handleImageChange = (e) => {
    setImage(e.target.files[0]);
  };

  // =========================
  // AI SUMMARY
  // =========================
  const generateSummary = async () => {

    if (!resume.fullName || !role || !resume.skills || !experience) {
      alert("Fill Name, Role, Skills & Experience");
      return;
    }

    setLoadingAI(true);

    try {
      const res = await axios.get(
        `${process.env.REACT_APP_API_URL}/api/ai/generate-summary`,
        {
          params: {
            fullName: resume.fullName,
            role: role,
            skills: resume.skills,
            yearsOfExperience: experience
          }
        }
      );

      setResume((prev) => ({
        ...prev,
        summary: res.data
      }));

    } catch (err) {
      console.error(err);
      alert("AI Failed");
    }

    setLoadingAI(false);
  };

  // =========================
  // SAVE RESUME (🔥 FIXED)
  // =========================
  const saveResume = async () => {

    try {

      const formData = new FormData();

      // ✅ IMPORTANT: send JSON as string
      formData.append(
        "resume",
        new Blob([JSON.stringify(resume)], { type: "application/json" })
      );

      // ✅ attach image if exists
      if (image) {
        formData.append("image", image);
      }

      console.log("SENDING FORM DATA");

      await axios.post(
        `${process.env.REACT_APP_API_URL}/api/resumes/user/3`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      );

      alert("✅ Resume Saved Successfully");

      // RESET
      setResume({
        fullName: "",
        email: "",
        phone: "",
        location: "",
        linkedin: "",
        github: "",
        skills: "",
        summary: "",
        template: "modern"
      });

      setRole("");
      setExperience("");
      setImage(null);

    } catch (error) {
      console.error(error);
      alert("❌ Error saving resume");
    }
  };

  // =========================
  // UI
  // =========================
  return (
    <div className="page">

      <h1>Create Resume</h1>

      <div className="card">

        {/* BASIC */}
        <input name="fullName" placeholder="Full Name" value={resume.fullName} onChange={handleChange} />
        <input name="email" placeholder="Email" value={resume.email} onChange={handleChange} />
        <input name="phone" placeholder="Phone" value={resume.phone} onChange={handleChange} />
        <input name="location" placeholder="Location" value={resume.location} onChange={handleChange} />

        {/* IMAGE */}
        <input type="file" accept="image/*" onChange={handleImageChange} />

        {/* AI INPUTS */}
        <input placeholder="Role (Java Developer)" value={role} onChange={(e) => setRole(e.target.value)} />
        <input type="number" placeholder="Years of Experience" value={experience} onChange={(e) => setExperience(e.target.value)} />

        {/* SOCIAL */}
        <input name="linkedin" placeholder="LinkedIn" value={resume.linkedin} onChange={handleChange} />
        <input name="github" placeholder="GitHub" value={resume.github} onChange={handleChange} />

        {/* SKILLS */}
        <textarea name="skills" placeholder="Skills (comma separated)" rows="3" value={resume.skills} onChange={handleChange} />

        {/* AI BUTTON */}
        <div className="btn-row">
          <button type="button" className="btn purple" onClick={generateSummary}>
            {loadingAI ? "Generating..." : "🤖 Generate AI Summary"}
          </button>
        </div>

        {/* SUMMARY */}
        <textarea name="summary" placeholder="Professional Summary" rows="5" value={resume.summary} onChange={handleChange} />

        {/* TEMPLATE */}
        <h3>Select Template</h3>

        <div className="template-grid">

          <div
            className={`template-card ${resume.template === "modern" ? "active" : ""}`}
            onClick={() => setResume((prev) => ({ ...prev, template: "modern" }))}
          >
            Modern
          </div>

          <div
            className={`template-card ${resume.template === "minimal" ? "active" : ""}`}
            onClick={() => setResume((prev) => ({ ...prev, template: "minimal" }))}
          >
            Minimal
          </div>

          <div
            className={`template-card ${resume.template === "creative" ? "active" : ""}`}
            onClick={() => setResume((prev) => ({ ...prev, template: "creative" }))}
          >
            Creative
          </div>

        </div>

        {/* SAVE */}
        <div className="btn-row">
          <button type="button" className="btn primary" onClick={saveResume}>
            Save Resume
          </button>
        </div>

      </div>
    </div>
  );
}

export default CreateResume;