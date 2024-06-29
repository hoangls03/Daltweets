import React, {useState} from 'react'
import axios from "axios";

const CreatePost = () => {
  const [id,setId] = useState(1);
  const [text,setText] = useState('');
  const [dateCreated,setDateCreated] = useState(null);
  const [isDeleted,setIsDeleted] = useState(false);
  const [isEdited,setIsEdited] = useState(false);

  const sendPost = (e) => {
    e.preventDefault();
    const user = JSON.parse(localStorage.getItem('user'));
    const formData = {
      id,
      text,
      user,
      dateCreated,
      isDeleted,
      isEdited,
    }
    axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/api/post/create`,
      formData
    ) .then((response) => {
        alert('Post sent success');
        console.log(response);
      })
      .catch((error) => {
        alert('Post not sent');
        console.log(error);
      });
  }
  return (
    <div className="w-screen h-screen">
        <div className="w-2/3">
        CreatePost
        <form >
          <textarea className="w-full h-40 border border-gold" type = "text" value = {text} onChange={(e) => setText(e.target.value)} placeholder="What's on your mind"/>
          <div className="w-full">
            <div className="justify-end flex"> 
              <button className="bg-yellow-400 hover:bg-gold text-black font-bold py-2 px-4 rounded  " type="submit" onClick={sendPost}>Post</button>
              </div>
          </div>
        </form>
        </div>
    </div>
  )
}

export default CreatePost