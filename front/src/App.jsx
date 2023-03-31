import { useRoutes, Outlet, BrowserRouter } from 'react-router-dom';
import Creator from './components/Creator';
import Reader from './components/Reader';
import Header from './components/Header';
import Manga from './components/Manga';

function Router(props) {
  return useRoutes(props.rootRoute);
}

export default function App() {
  const routes = [
    { index: true, element: <Creator /> },
    { path: 'creator', element: <Creator />, label: 'Creator' },
    { path: 'reader', element: <Reader />, label: 'Reader' },
    { path: 'manga', element: <Manga />, label: 'Manga' },
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