import type { ButtonHTMLAttributes } from 'react';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'danger' | 'secondary' | 'success';
}

export default function Button({ variant = 'primary', className = '', children, ...props }: Props) {
  return (
    <button className={`btn-${variant} ${className}`} data-testid={props['data-testid']} {...props}>
      {children}
    </button>
  );
}
