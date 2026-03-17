import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../services/api';
import { useAuthStore } from '../../stores/useAuthStore';
import Button from '../../components/atoms/Button';

export default function TableSetupPage() {
  const [storeCode, setStoreCode] = useState('');
  const [tableNumber, setTableNumber] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const setTableAuth = useAuthStore((s) => s.setTableAuth);
  const nav = useNavigate();

  const handleSubmit = async () => {
    try {
      const res = await api.post<{ storeId: number; tableId: number; sessionToken: string | null }>('/auth/table', {
        storeCode, tableNumber: Number(tableNumber), password,
      });
      setTableAuth(storeCode, Number(tableNumber), res.tableId, res.storeId, res.sessionToken);
      nav('/customer/menu');
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : '인증 실패');
    }
  };

  return (
    <div className="container" style={{ maxWidth: 400, marginTop: 80 }}>
      <div className="card">
        <h2 className="text-xl font-bold mb-16 text-center">☕ 테이블 설정</h2>
        {error && <p style={{ color: 'red', marginBottom: 8 }}>{error}</p>}
        <div className="form-group">
          <label>매장 코드</label>
          <input value={storeCode} onChange={(e) => setStoreCode(e.target.value)} placeholder="CAFE01" data-testid="setup-store-code" />
        </div>
        <div className="form-group">
          <label>테이블 번호</label>
          <input type="number" value={tableNumber} onChange={(e) => setTableNumber(e.target.value)} placeholder="1" data-testid="setup-table-number" />
        </div>
        <div className="form-group">
          <label>비밀번호</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="1234" data-testid="setup-password" />
        </div>
        <Button onClick={handleSubmit} style={{ width: '100%' }} data-testid="setup-submit">설정 완료</Button>
      </div>
    </div>
  );
}
