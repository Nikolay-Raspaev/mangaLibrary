import { useRoutes, Outlet, BrowserRouter } from 'react-router-dom';
import Creator from './MainS/Creator';
import Reader from './MainS/Reader';
import Header from './components/Header';
import CreatorAction from './Main/CreatorAction';
import ReaderAction from './Main/ReaderAction';
import MangaPage from './Main/MangaPage';
import Catalog from './Main/Catalog';

function Router(props) {
  return useRoutes(props.rootRoute);
}

export default function App() {
  const routes = [
    { index: true, element: <Reader /> },
    { path: 'creator', element: <Creator />, label: 'Creator' },
    { path: 'reader', element: <Reader />, label: 'Reader' },
    { path: 'creatorAction', element: <CreatorAction />, label: 'CreatorAction' },
    { path: 'readerAction', element: <ReaderAction />, label: 'ReaderAction' },
    { path: 'catalog', element: <Catalog />, label: 'Catalog' },
    { path: 'mangapage', element: <MangaPage /> },
  ];
  const links = routes.filter(route => route.hasOwnProperty('label'));
  const rootRoute = [
    { path: '/', element: render(links), children: routes }
  ];

  function render(links) {
    return (
      <>
      <Header links={links} />
        <Outlet />
      </>
    );
  }

  return (
    <BrowserRouter>
      <Router rootRoute={ rootRoute } />
    </BrowserRouter>
  );
}