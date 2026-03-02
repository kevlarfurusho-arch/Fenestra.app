import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  server: {
    // Proxy API requests to the backend server. Remove for production.
    proxy: {
      '/api': 'http://localhost:8080',
    },
    host: true,
  },
  plugins: [react()],
})
