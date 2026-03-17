import { describe, it, expect } from 'vitest';
import { render, screen } from '@testing-library/react';
import Button from '../../components/atoms/Button';

describe('Button', () => {
  it('renders with text', () => {
    render(<Button data-testid="test-btn">클릭</Button>);
    expect(screen.getByTestId('test-btn')).toHaveTextContent('클릭');
  });

  it('applies variant class', () => {
    render(<Button variant="danger" data-testid="danger-btn">삭제</Button>);
    expect(screen.getByTestId('danger-btn')).toHaveClass('btn-danger');
  });
});
