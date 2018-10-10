var path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
var webpack = require('webpack');

module.exports = {
    entry: './src/main/js/index.js',
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/built'),
        publicPath: '/built/',
        filename: 'bundle.js'
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
              include: /fonts/,
              loader: 'file-loader',
              options: {
                  name: '[path][name].[ext]',
                  outputPath: 'fonts/'
              }
          }
        ]
      }
};