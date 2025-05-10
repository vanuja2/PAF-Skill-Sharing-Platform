import React from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { Github } from 'lucide-react';
import PropTypes from 'prop-types';

export function SocialLogin({ onSuccess, onError }) {
  const { loginWithPopup, isLoading } = useAuth0();

  const handleGoogleLogin = async () => {
    try {
      await loginWithPopup({
        connection: 'google-oauth2',
        prompt: 'select_account',
      });
      onSuccess?.();
    } catch (error) {
        console.error('Google login failed:', error);
        alert(error?.error_description || error?.message || 'Unknown error');
        onError?.(error);
      }
  };

  const handleGithubLogin = async () => {
    try {
      await loginWithPopup({
        connection: 'github',
        prompt: 'select_account',
      });
      onSuccess?.();
    } catch (error) {
      console.error('GitHub login failed:', error);
      onError?.(error);
    }
  };

  return (
    <div className="space-y-4">
      <div className="relative">
        <div className="absolute inset-0 flex items-center">
          <div className="w-full border-t border-gray-300" />
        </div>
        <div className="relative flex justify-center text-sm">
          <span className="px-2 bg-white text-gray-500">Or continue with</span>
        </div>
      </div>

      <div className="grid grid-cols-2 gap-4">
        <button
          type="button"
          onClick={handleGoogleLogin}
          disabled={isLoading}
          className="flex items-center justify-center w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <img
            src="https://www.google.com/favicon.ico"
            alt="Google"
            className="h-5 w-5 mr-2"
          />
          Google
        </button>

        <button
          type="button"
          onClick={handleGithubLogin}
          disabled={isLoading}
          className="flex items-center justify-center w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <Github className="h-5 w-5 mr-2" />
          GitHub
        </button>
      </div>
    </div>
  );
}

SocialLogin.propTypes = {
  onSuccess: PropTypes.func,
  onError: PropTypes.func,
};