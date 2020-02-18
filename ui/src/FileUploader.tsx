import React, { FormEvent } from 'react'
import gql from 'graphql-tag'
import { Mutation } from 'react-apollo'

const fileUpload = (e: FormEvent<HTMLFormElement>) => {
  const file = e.target
  debugger
  console.log(JSON.stringify(file))
}

const UPLOAD_FILE = gql`
  mutation imageUpload($file: Upload!, $fileName: String) {
    imageUpload(file: $file, fileName: $fileName) {
      id
      url
    }
  }
`

const FileUploader: React.FC = () => {
  return (
    <div>
      <Mutation mutation={UPLOAD_FILE}>
        {(singleUpload, { loading }) => {
          return (<form onSubmit={fileUpload} encType={'multipart/form-data'}>
            <input name={'document'} type={'file'}
                   onChange={({ target: { files } }) => {
                     if(files == null) {
                       return
                     }
                     debugger
                     const file = files[0]
                     file && singleUpload({ variables: { file: file, fileName: file.name } })
                   }}/>
            {loading && <p>Loading.....</p>}
          </form>)
        }
        }
      </Mutation>
    </div>
  )
}

export default FileUploader