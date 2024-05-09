import React, { memo, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useSearchParams } from 'react-router-dom';

import { changeTab, homePageUnloaded } from '../../reducers/articleList';
import Banner from './Banner';
import MainView from './MainView';
import TagsSidebar from '../../features/tags/TagsSidebar';
import Greetings from '../../features/greetings/Greetings';
import { selectIsAuthenticated } from '../../features/auth/authSlice';

/**
 * Home screen component
 *
 * @example
 * <Home />
 */
function Home() {
  const dispatch = useDispatch();
  const isAuthenticated = useSelector(selectIsAuthenticated);
  const [queryParameters] = useSearchParams();

  useEffect(() => {
    const defaultTab = isAuthenticated ? 'feed' : 'all';
    const fetchArticles = dispatch(changeTab(defaultTab));

    return () => {
      dispatch(homePageUnloaded());
      fetchArticles.abort();
    };
  }, []);

  const name = queryParameters.get("name");

  return (
    <div className="home-page">
      <Banner />

      <div className="container page">
        <div className="row">
          <MainView />

          <div className="col-md-3">
            {name && <Greetings name={name} />}
            <TagsSidebar />
          </div>
        </div>
      </div>
    </div>
  );
}

export default memo(Home);
