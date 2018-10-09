var path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require('webpack');

module.exports = {
    entry: './src/main/js/index.js',
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
      module: {
        loaders: [
          {
            test: /\.js|.jsx?$/,
            exclude: /(node_modules)/,
            loader: 'babel-loader',
            query: {
              presets: ['react', 'es2015']
            }
          },
          {
            test: /\.s?css$/,  // scss & css files
            use: [
                'style-loader',
                'css-loader',
                'sass-loader',
                'resolve-url-loader'
                ]
          },
          {
            test: /\.svg$/,
            use: [
              {
                loader: "babel-loader"
              },
              {
                loader: "react-svg-loader",
                options: {
                  jsx: true // true outputs JSX tags
                }
              }
            ]
          },
          {
              test: /\.svg$/,
              loader: 'svg-inline-loader'
          },
          {
            test: /\.(gif|svg|jpg|png)$/,
            loader: "file-loader",
          },
          {
            test: /\.css$/,
            loader: "style-loader!css-loader"
          },
          {
              test: /\.(woff2?|ttf|otf|eot|svg)$/,
              exclude: /node_modules/,
              loader: 'file-loader',
              options: {
                  name: '[path][name].[ext]',
                  outputPath: 'fonts/'
              }
          }
        ]
      }
};