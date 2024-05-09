import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

import agent from '../../agent';
import { Status } from '../../common/utils';

export const getGreeting = createAsyncThunk('greeting/getGreeting', async (name) => {
  const { greeting } = await agent.Greeting.get({name});

  return greeting;
});

const initialState = {
  status: Status.IDLE,
  greeting: null,
};

const greetingSlice = createSlice({
  name: 'greeting',
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getGreeting.pending, (state) => {
        state.status = Status.LOADING;
      })
      .addCase(getGreeting.fulfilled, (_, action) => ({
        status: Status.SUCCESS,
        greeting: action.payload,
      }));
  },
});


const selectGreetingState = (state) => state.greeting;

export const selectGreeting = (state) => selectGreetingState(state).greeting;


export const selectIsLoading = (state) =>
selectGreetingState(state).status === Status.LOADING;

export default greetingSlice.reducer;
