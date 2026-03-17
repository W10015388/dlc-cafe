export function subscribeSSE(url: string, onEvent: (type: string, data: unknown) => void): EventSource {
  const es = new EventSource(`/api/v1/events${url}`);

  es.addEventListener('NEW_ORDER', (e) => onEvent('NEW_ORDER', JSON.parse(e.data)));
  es.addEventListener('STATUS_CHANGED', (e) => onEvent('STATUS_CHANGED', JSON.parse(e.data)));
  es.addEventListener('ORDER_DELETED', (e) => onEvent('ORDER_DELETED', JSON.parse(e.data)));
  es.addEventListener('SESSION_COMPLETED', (e) => onEvent('SESSION_COMPLETED', JSON.parse(e.data)));

  es.onerror = () => {
    // EventSource auto-reconnects
  };

  return es;
}
