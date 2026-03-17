interface Props {
  status: 'PENDING' | 'PREPARING' | 'COMPLETED';
}

const labels: Record<string, string> = { PENDING: '대기중', PREPARING: '준비중', COMPLETED: '완료' };

export default function Badge({ status }: Props) {
  return <span className={`badge badge-${status.toLowerCase()}`}>{labels[status]}</span>;
}
