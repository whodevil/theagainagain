import React from 'react'
import fetch from 'isomorphic-fetch'
import GraphiQL from 'graphiql'
import 'graphiql/graphiql.css'

const graphQLFetcher = (graphQLParams:any) => {
  return fetch(window.location.origin + '/graphql', {
    method: 'post',
    headers: { 'Content-Type': 'application/json', 'X-Requested-With': 'graphiql'},
    body: JSON.stringify(graphQLParams),
  }).then(response => response.json())
}

const Graphql: React.FC = () => {
  var divStyle = {
    height: "100vh"
  }
  return (
    <div style={divStyle}>
      <GraphiQL fetcher={graphQLFetcher} />
    </div>
  )
}

export default Graphql
