import React from 'react'
import { Link } from 'react-router-dom'
import { gql } from 'apollo-boost'
import { useQuery } from '@apollo/react-hooks'

interface ServiceDefinition {
  version: string;
}

interface ServiceDefinitionData {
  serviceDefinition: ServiceDefinition;
}

const GET_SERVICE_DEFINITION = gql`
    query  {
        serviceDefinition {
            version
        }
    }
`

const Version: React.FC = () => {
  const { loading, data } = useQuery<ServiceDefinitionData>(
    GET_SERVICE_DEFINITION,
  )

  return (
    <span>
      {loading ? (
        <span>loading</span>
      ):(
        <span>{data?.serviceDefinition.version}</span>
      )}
    </span>
  )
}

const Home: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <span>bro</span>
      </header>
      <footer>
        <span> <Version /> <Link to="/graphiql">Graphiql</Link>  <Link to="/fileupload">upload</Link></span>
      </footer>
    </div>
  )
}

export default Home