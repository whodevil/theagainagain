import gql from 'graphql-tag';
import * as ApolloReactCommon from '@apollo/react-common';
import * as ApolloReactHooks from '@apollo/react-hooks';
export type Maybe<T> = T | null;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string,
  String: string,
  Boolean: boolean,
  Int: number,
  Float: number,
  Upload: any,
};

export type Image = {
   __typename: 'Image',
  id?: Maybe<Scalars['String']>,
  url?: Maybe<Scalars['String']>,
  filename: Scalars['String'],
  mimetype: Scalars['String'],
  encoding: Scalars['String'],
};

export type Mutation = {
   __typename: 'Mutation',
  imageUpload: Image,
};


export type MutationImageUploadArgs = {
  file: Scalars['Upload']
};

export type Query = {
   __typename: 'Query',
  serviceDefinition?: Maybe<ServiceDefinition>,
};

export type ServiceDefinition = {
   __typename: 'ServiceDefinition',
  version: Scalars['String'],
};


export type ImageUploadMutationVariables = {
  file: Scalars['Upload']
};


export type ImageUploadMutation = (
  { __typename: 'Mutation' }
  & { imageUpload: (
    { __typename: 'Image' }
    & Pick<Image, 'id'>
  ) }
);

export type Unnamed_1_QueryVariables = {};


export type Unnamed_1_Query = (
  { __typename: 'Query' }
  & { serviceDefinition: Maybe<(
    { __typename: 'ServiceDefinition' }
    & Pick<ServiceDefinition, 'version'>
  )> }
);


export const ImageUploadDocument = gql`
    mutation imageUpload($file: Upload!) {
  imageUpload(file: $file) {
    id
  }
}
    `;
export type ImageUploadMutationFn = ApolloReactCommon.MutationFunction<ImageUploadMutation, ImageUploadMutationVariables>;

/**
 * __useImageUploadMutation__
 *
 * To run a mutation, you first call `useImageUploadMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useImageUploadMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [imageUploadMutation, { data, loading, error }] = useImageUploadMutation({
 *   variables: {
 *      file: // value for 'file'
 *   },
 * });
 */
export function useImageUploadMutation(baseOptions?: ApolloReactHooks.MutationHookOptions<ImageUploadMutation, ImageUploadMutationVariables>) {
        return ApolloReactHooks.useMutation<ImageUploadMutation, ImageUploadMutationVariables>(ImageUploadDocument, baseOptions);
      }
export type ImageUploadMutationHookResult = ReturnType<typeof useImageUploadMutation>;
export type ImageUploadMutationResult = ApolloReactCommon.MutationResult<ImageUploadMutation>;
export type ImageUploadMutationOptions = ApolloReactCommon.BaseMutationOptions<ImageUploadMutation, ImageUploadMutationVariables>;