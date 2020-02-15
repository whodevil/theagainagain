import React, { useState } from 'react'
import { useImageUploadMutation } from './generated/graphql'

const FileUploader: React.FC = () => {
  const [mutate] = useImageUploadMutation()
  let [files, setFiles] = useState()

  const onChange = (event: React.FormEvent<HTMLInputElement>) => {
    console.log(event)
    setFiles(event.currentTarget.value)
  }
  return (
    <div>
      <input type="file" required onChange={onChange}/>
      <button onClick={() => mutate(files)}>dude</button>
    </div>
  )
}

export default FileUploader