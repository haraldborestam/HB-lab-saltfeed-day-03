import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getGreeting, selectIsLoading, selectGreeting } from './greetingSlice';

/**
 * Show all tags in the sidebar
 *
 * @example
 * <Greetings />
 */
function Greetings({ name }) {
  const dispatch = useDispatch();
  const greeting = useSelector(selectGreeting);
  const isLoading = useSelector(selectIsLoading);

  useEffect(() => {
    const fetchGreeting = dispatch(getGreeting(name));

    return () => {
      fetchGreeting.abort();
    };
  }, []);

  return (
    <div className="greeting">
      <h3>Greeter:</h3>

      <p>
        {isLoading ? (
          <span>Loading Greeting...</span>
        ) : (
          greeting
        )}
      </p>
    </div>
  );
}

export default Greetings;
