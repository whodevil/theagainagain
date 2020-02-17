import React from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'

import { ApolloProvider } from '@apollo/react-hooks'
import './App.css'
import Home from './Home'
import Graphql from './Graphql'

import ApolloClient from 'apollo-client'
import { InMemoryCache } from 'apollo-cache-inmemory'
import { createUploadLink } from 'apollo-upload-client'
import FileUploader from './FileUploader'

const apolloCache = new InMemoryCache()
const uploadLink = createUploadLink({
  uri: '/graphql',
  headers: {
    'X-Requested-With': 'apollo',
    'keep-alive': 'true'
  }
})

const client = new ApolloClient({
  cache: apolloCache,
  link: uploadLink
})

const App: React.FC = () => {
  return (
    <ApolloProvider client={client}>
      <Router>

        <div>
          <Route path="/" exact component={Home}/>
          <Route path="/fileupload" component={FileUploader}/>
          <Route path="/graphiql" component={Graphql}/>
        </div>

      </Router>
    </ApolloProvider>
  )
}

export default App
